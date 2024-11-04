package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class SettingResponseTest {

    @Test
    void testToResponse_SettingIsNull() {
        assertNull(SettingResponse.toResponse(null));
    }
}