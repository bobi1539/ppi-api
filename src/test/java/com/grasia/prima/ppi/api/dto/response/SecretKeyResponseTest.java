package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class SecretKeyResponseTest {

    @Test
    void testToResponse_SecretKeyIsNull() {
        assertNull(SecretKeyResponse.toResponse(null));
    }
}