package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.AuthService;
import com.grasia.prima.ppi.api.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        MUser user = getByUsername(request.getUsername());
        verifyPassword(request.getPassword(), user.getPassword());
        return buildLoginResponse(generateToken(user));
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
                .username(user.getUsername())
                .build();
        return jwtService.generateToken(dto);
    }

    private LoginResponse buildLoginResponse(String jwt) {
        return LoginResponse.builder().jwt(jwt).build();
    }
}
