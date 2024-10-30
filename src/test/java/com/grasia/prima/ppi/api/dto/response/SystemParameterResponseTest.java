package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class SystemParameterResponseTest {

    @Test
    void testToResponse_SystemParameterIsNull() {
        assertNull(SystemParameterResponse.toResponse(null));
    }
}