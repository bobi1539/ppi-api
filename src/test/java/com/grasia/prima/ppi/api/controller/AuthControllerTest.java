package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.request.RefreshTokenRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController controller;

    @Mock
    private AuthService service;

    private final LoginRequest loginRequest = ObjectDummy.getLoginRequest();
    private final LoginResponse loginResponse = ObjectDummy.getLoginResponse();
    private final RefreshTokenRequest refreshTokenRequest = ObjectDummy.getRefreshTokenRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(service.login(loginRequest)).thenReturn(loginResponse);

        LoginResponse response = controller.login(loginRequest);
        assertEquals(loginResponse.getJwt(), response.getJwt());
        assertEquals(loginResponse.getRefreshToken(), response.getRefreshToken());

        verify(service, times(1)).login(loginRequest);
    }

    @Test
    void testLoginWithRefreshToken() {
        when(service.loginWithRefreshToken(refreshTokenRequest)).thenReturn(loginResponse);

        LoginResponse response = controller.loginWithRefreshToken(refreshTokenRequest);
        assertEquals(loginResponse.getJwt(), response.getJwt());
        assertEquals(loginResponse.getRefreshToken(), response.getRefreshToken());

        verify(service, times(1)).loginWithRefreshToken(refreshTokenRequest);
    }
}