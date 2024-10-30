package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class NewsletterResponseTest {

    @Test
    void testToResponse_NewsletterIsNull() {
        assertNull(NewsletterResponse.toResponse(null));
    }
}