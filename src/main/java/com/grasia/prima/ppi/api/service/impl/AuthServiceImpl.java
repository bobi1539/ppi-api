package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.request.RefreshTokenRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
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
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LogAuthRepository logAuthRepository;
    private final UserRoleMenuService userRoleMenuService;

    @Override
    public LoginResponse login(LoginRequest request) {
        MUser user = getByUsername(request.getUsername());
        verifyPassword(request.getPassword(), user.getPassword());

        String jwt = generateToken(user);
        String refreshToken = saveLogAuth(user);
        return buildLoginResponse(jwt, refreshToken, user.getUserRole().getId());
    }

    @Override
    public LoginResponse loginWithRefreshToken(RefreshTokenRequest request) {
        LogAuth logAuth = findLogAuthByRefreshToken(request.getRefreshToken());
        String jwt = generateToken(logAuth.getUser());
        return buildLoginResponse(jwt, logAuth.getRefreshToken(), logAuth.getUser().getUserRole().getId());
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

    private String generateToken(MUser user) {
        JwtComponentDto dto = JwtComponentDto.builder()
                .userId(user.getId().toString())
                .username(user.getUsername())
                .userFullName(user.getFullName())
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

    private LoginResponse buildLoginResponse(String jwt, String refreshToken, Long userRoleId) {
        return LoginResponse.builder()
                .jwt(jwt)
                .refreshToken(refreshToken)
                .userRoleMenu(getUserRoleMenu(userRoleId))
                .build();
    }

    private UserRoleMenuResponse getUserRoleMenu(Long userRoleId) {
        return userRoleMenuService.findByUserRoleId(userRoleId);
    }

    private LogAuth findLogAuthByRefreshToken(String refreshToken) {
        return logAuthRepository.findByRefreshTokenAndRefreshTokenExpiryAfter(refreshToken, LocalDate.now())
                .orElseThrow(() -> new BusinessException(GlobalMessage.REFRESH_TOKEN_NOT_VALID));
    }
}
