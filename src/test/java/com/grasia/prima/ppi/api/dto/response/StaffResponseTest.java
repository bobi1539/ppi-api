package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StaffResponseTest {

    @Test
    void testToResponse_StaffIsNull() {
        assertNull(StaffResponse.toResponse(null));
    }

    @Test
    void testToResponses_StaffListIsNull() {
        List<StaffResponse> responses = StaffResponse.toResponses(null);
        assertTrue(responses.isEmpty());
    }
}