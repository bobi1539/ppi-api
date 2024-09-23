package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.LoginRequest;
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

    private final LoginRequest request = ObjectDummy.getLoginRequest();
    private final LoginResponse response = ObjectDummy.getLoginResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        when(service.login(request)).thenReturn(response);

        LoginResponse loginResponse = controller.login(request);
        assertEquals(response, loginResponse);

        verify(service, times(1)).login(request);
    }
}