package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class StaffResponseTest {

    @Test
    void testToResponse_StaffIsNull() {
        assertNull(StaffResponse.toResponse(null));
    }
}