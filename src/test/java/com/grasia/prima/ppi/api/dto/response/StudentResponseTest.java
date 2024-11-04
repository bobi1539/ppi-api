package com.grasia.prima.ppi.api.dto.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class StudentResponseTest {

    @Test
    void testToResponse_StudentIsNull() {
        assertNull(StudentResponse.toResponse(null));
    }
}