package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class DivisionResponseTest {

    @Test
    void testToResponse_DivisionIsNull() {
        assertNull(DivisionResponse.toResponse(null));
    }
}