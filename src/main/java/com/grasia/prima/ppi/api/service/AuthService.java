package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.LoginRequest;
import com.grasia.prima.ppi.api.dto.request.RefreshTokenRequest;
import com.grasia.prima.ppi.api.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse loginWithRefreshToken(RefreshTokenRequest request);
}
