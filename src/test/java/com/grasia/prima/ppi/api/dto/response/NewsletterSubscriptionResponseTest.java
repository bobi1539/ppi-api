package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class NewsletterSubscriptionResponseTest {

    @Test
    void testToResponse_SubscriptionIsNull() {
        assertNull(NewsletterSubscriptionResponse.toResponse(null));
    }
}