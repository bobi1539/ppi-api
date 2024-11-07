package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class NewsletterEmailResponseTest {

    @Test
    void testToResponse_NewsletterEmailIsNull() {
        assertNull(NewsletterEmailResponse.toResponse(null));
    }
}