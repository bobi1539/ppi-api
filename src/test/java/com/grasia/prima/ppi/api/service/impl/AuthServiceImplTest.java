package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.request.RefreshTokenRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.entity.LogAuth;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.LogAuthRepository;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.JwtService;
import com.grasia.prima.ppi.api.service.UserRoleMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private LogAuthRepository logAuthRepository;

    @Mock
    private UserRoleMenuService userRoleMenuService;

    private final LoginRequest loginRequest = ObjectDummy.getLoginRequest();
    private final MUser user = ObjectDummy.getUser();
    private final LogAuth logAuth = ObjectDummy.getLogAuth();
    private final RefreshTokenRequest refreshTokenRequest = ObjectDummy.getRefreshTokenRequest();
    private final UserRoleMenuResponse userRoleMenuResponse = ObjectDummy.getUserRoleMenuResponse();
    private static final String USERNAME = "admin";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn(ObjectDummy.JWT);
        when(logAuthRepository.save(any())).thenReturn(logAuth);
        when(userRoleMenuService.findByUserRoleId(any())).thenReturn(userRoleMenuResponse);

        LoginResponse response = authService.login(loginRequest);
        assertEquals(ObjectDummy.JWT, response.getJwt());
        assertNotNull(response.getRefreshToken());

        verify(userRepository).findByUsername(USERNAME);
        verify(passwordEncoder).matches(anyString(), anyString());
        verify(jwtService).generateToken(any());
        verify(logAuthRepository).save(any());
        verify(userRoleMenuService).findByUserRoleId(any());
    }

    @Test
    void testLogin_UsernameNotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> authService.login(loginRequest));
        assertEquals(GlobalMessage.WRONG_USERNAME_OR_PASSWORD.status, e.getStatus());
        assertEquals(GlobalMessage.WRONG_USERNAME_OR_PASSWORD.message, e.getMessage());

        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    void testLogin_PasswordNotMatch() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        BusinessException e = assertThrows(BusinessException.class, () -> authService.login(loginRequest));
        assertEquals(GlobalMessage.WRONG_USERNAME_OR_PASSWORD.status, e.getStatus());
        assertEquals(GlobalMessage.WRONG_USERNAME_OR_PASSWORD.message, e.getMessage());

        verify(userRepository).findByUsername(USERNAME);
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void tesLoginWithRefreshToken_Success() {
        when(logAuthRepository.findByRefreshTokenAndRefreshTokenExpiryAfter(anyString(), any()))
                .thenReturn(Optional.of(logAuth));
        when(jwtService.generateToken(any())).thenReturn(ObjectDummy.JWT);
        when(userRoleMenuService.findByUserRoleId(any())).thenReturn(userRoleMenuResponse);

        LoginResponse response = authService.loginWithRefreshToken(refreshTokenRequest);
        assertEquals(ObjectDummy.JWT, response.getJwt());
        assertEquals(logAuth.getRefreshToken(), response.getRefreshToken());

        verify(logAuthRepository).findByRefreshTokenAndRefreshTokenExpiryAfter(anyString(), any());
        verify(jwtService).generateToken(any());
        verify(userRoleMenuService).findByUserRoleId(any());
    }

    @Test
    void tesLoginWithRefreshToken_RefreshTokenNotValid() {
        when(logAuthRepository.findByRefreshTokenAndRefreshTokenExpiryAfter(anyString(), any()))
                .thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> authService.loginWithRefreshToken(refreshTokenRequest));
        assertEquals(GlobalMessage.REFRESH_TOKEN_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.REFRESH_TOKEN_NOT_VALID.message, e.getMessage());

        verify(logAuthRepository).findByRefreshTokenAndRefreshTokenExpiryAfter(anyString(), any());
    }
}