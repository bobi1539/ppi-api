package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class SubMenuResponseTest {

    @Test
    void testToResponse_SubMenuIsNull() {
        assertNull(SubMenuResponse.toResponse(null));
    }
}