package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.dto.JwtComponentDto;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

    @Mock
    private AppConfig appConfig;

    private final JwtComponentDto jwtComponentDto = ObjectDummy.getJwtComponentDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateToken() {
        when(appConfig.getJwtSecret()).thenReturn(ObjectDummy.JWT_SECRET);
        when(appConfig.getJwtExpiredDuration()).thenReturn(ObjectDummy.JWT_EXPIRED_DURATION);

        String token = jwtService.generateToken(jwtComponentDto);
        assertNotNull(token);

        verify(appConfig, times(1)).getJwtSecret();
        verify(appConfig, times(1)).getJwtExpiredDuration();
    }

    @Test
    void testExtractToken() {
        when(appConfig.getJwtSecret()).thenReturn(ObjectDummy.JWT_SECRET);
        when(appConfig.getJwtExpiredDuration()).thenReturn(ObjectDummy.JWT_EXPIRED_DURATION);

        String token = jwtService.generateToken(jwtComponentDto);
        JwtComponentDto result = jwtService.extractToken(token);
        assertEquals(jwtComponentDto.getUserId(), result.getUserId());
        assertEquals(jwtComponentDto.getUsername(), result.getUsername());
        assertEquals(jwtComponentDto.getUserFullName(), result.getUserFullName());

        verify(appConfig, times(2)).getJwtSecret();
        verify(appConfig, times(1)).getJwtExpiredDuration();
    }
}