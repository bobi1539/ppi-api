package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.JwtComponentDto;

public interface JwtService {

    String generateToken(JwtComponentDto dto);

    String extractUsername(String token);
}
