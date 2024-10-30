package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class EventResponseTest {

    @Test
    void testToResponse_EventIsNull() {
        assertNull(EventResponse.toResponse(null));
    }
}