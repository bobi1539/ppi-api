package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemParameterListResponseTest {

    @Test
    void testToResponse_SystemParameterListIsNull() {
        assertNull(SystemParameterListResponse.toResponse(null));
    }
}