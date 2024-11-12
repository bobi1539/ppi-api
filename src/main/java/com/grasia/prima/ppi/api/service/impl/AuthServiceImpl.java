package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.request.RefreshTokenRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.entity.LogAuth;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.repository.LogAuthRepository;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.AuthService;
import com.grasia.prima.ppi.api.service.JwtService;
import com.grasia.prima.ppi.api.service.UserRoleMenuService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LogAuthRepository logAuthRepository;
    private final UserRoleMenuService userRoleMenuService;

    @Transactional
    @Override
    public LoginResponse login(LoginRequest request) {
        MUser user = getByUsername(request.getUsername());
        verifyPassword(request.getPassword(), user.getPassword());
        verifyIsActive(user);

        String jwt = generateToken(user);
        String refreshToken = saveLogAuth(user);
        List<String> routes = getRoutes(user);
        return buildLoginResponse(jwt, refreshToken, routes);
    }

    @Override
    public LoginResponse logout(RefreshTokenRequest request) {
        LogAuth logAuth = findLogAuthByRefreshToken(request.getRefreshToken());
        logAuthRepository.delete(logAuth);
        return buildLoginResponse(null, logAuth.getRefreshToken(), Collections.emptyList());
    }

    @Transactional
    @Override
    public LoginResponse loginWithRefreshToken(RefreshTokenRequest request) {
        LogAuth logAuth = findLogAuthByRefreshToken(request.getRefreshToken());
        String jwt = generateToken(logAuth.getUser());
        List<String> routes = getRoutes(logAuth.getUser());
        return buildLoginResponse(jwt, logAuth.getRefreshToken(), routes);
    }

    private MUser getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(GlobalMessage.WRONG_USERNAME_OR_PASSWORD));
    }

    private void verifyPassword(String rawPassword, String hashPassword) {
        boolean matches = passwordEncoder.matches(rawPassword, hashPassword);
        if (!matches) {
            throw new BusinessException(GlobalMessage.WRONG_USERNAME_OR_PASSWORD);
        }
    }

    private void verifyIsActive(MUser user) {
        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new BusinessException(GlobalMessage.ACCOUNT_NOT_ACTIVE);
        }
    }

    private String generateToken(MUser user) {
        JwtComponentDto dto = JwtComponentDto.builder()
                .userId(user.getId().toString())
                .username(user.getUsername())
                .userFullName(user.getName())
                .build();
        return jwtService.generateToken(dto);
    }

    private String saveLogAuth(MUser user) {
        LogAuth logAuth = LogAuth.builder()
                .refreshToken(StringHelper.random())
                .refreshTokenExpiry(LocalDate.now().plusMonths(1))
                .user(user)
                .build();
        logAuthRepository.save(logAuth);
        return logAuth.getRefreshToken();
    }

    private List<String> getRoutes(MUser user) {
        List<String> routes = new ArrayList<>();
        UserRoleMenuResponse userRoleMenu = userRoleMenuService.findByUserRoleId(user.getUserRole().getId());
        for (MenuResponse menu : userRoleMenu.getMenus()) {
            routes.add(menu.getRoute());
            for (SubMenuResponse subMenu : menu.getSubMenus()) {
                routes.add(subMenu.getRoute());
            }
        }
        return routes;
    }

    private LoginResponse buildLoginResponse(String jwt, String refreshToken, List<String> routes) {
        return LoginResponse.builder()
                .jwt(jwt)
                .refreshToken(refreshToken)
                .routes(routes)
                .build();
    }

    private LogAuth findLogAuthByRefreshToken(String refreshToken) {
        return logAuthRepository.findByRefreshTokenAndRefreshTokenExpiryAfter(refreshToken, LocalDate.now())
                .orElseThrow(() -> new BusinessException(GlobalMessage.REFRESH_TOKEN_NOT_VALID));
    }
}
