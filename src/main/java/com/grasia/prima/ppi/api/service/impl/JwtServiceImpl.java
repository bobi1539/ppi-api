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
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtServiceImpl implements JwtService {

    private final AppConfig appConfig;
    private static final String CLAIM_USERNAME = "username";

    @Override
    public String generateToken(JwtComponentDto dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USERNAME, dto.getUsername());
        return createToken(claims, dto.getUsername());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + getExpiredDuration()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
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
