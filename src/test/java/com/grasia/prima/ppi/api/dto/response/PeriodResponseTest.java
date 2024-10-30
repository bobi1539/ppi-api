package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodResponseTest {

    @Test
    void testToResponse_PeriodIsNull() {
        assertNull(PeriodResponse.toResponse(null));
    }
}