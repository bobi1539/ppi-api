package com.grasia.prima.ppi.api.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void testGetAllowEndpoints() {
        String[] allowEndpoints = SecurityConfig.getAllowEndpoints();
        assertEquals(4, allowEndpoints.length);
    }
}