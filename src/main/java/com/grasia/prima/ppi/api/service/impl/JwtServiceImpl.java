package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final AppConfig appConfig;
    private static final String CLAIM_USER_ID = "userId";
    private static final String CLAIM_USERNAME = "username";
    private static final String CLAIM_USER_FULL_NAME = "userFullName";

    @Override
    public String generateToken(JwtComponentDto dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USER_ID, dto.getUserId());
        claims.put(CLAIM_USERNAME, dto.getUsername());
        claims.put(CLAIM_USER_FULL_NAME, dto.getUserFullName());
        return createToken(claims, dto.getUsername());
    }

    @Override
    public JwtComponentDto extractToken(String token) {
        Claims claims = extractAllClaims(token);
        String userId = (String) claims.get(CLAIM_USER_ID);
        String username = (String) claims.get(CLAIM_USERNAME);
        String userFullName = (String) claims.get(CLAIM_USER_FULL_NAME);
        return JwtComponentDto.builder()
                .userId(userId)
                .username(username)
                .userFullName(userFullName)
                .build();
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiredDuration()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appConfig.getJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private long getExpiredDuration() {
        return Long.parseLong(appConfig.getJwtExpiredDuration());
    }
}
