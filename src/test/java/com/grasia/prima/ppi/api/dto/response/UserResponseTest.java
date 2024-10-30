package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class UserResponseTest {

    @Test
    void testToResponse_UserIsNull() {
        assertNull(UserResponse.toResponse(null));
    }
}