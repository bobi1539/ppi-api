package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class GalleryResponseTest {

    @Test
    void testToResponse_GalleryIsNull() {
        assertNull(GalleryResponse.toResponse(null));
    }
}