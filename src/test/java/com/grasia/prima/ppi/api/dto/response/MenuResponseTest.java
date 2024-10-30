package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class MenuResponseTest {

    @Test
    void testToResponse_MenuIsNull() {
        assertNull(MenuResponse.toResponse(null));
    }
}