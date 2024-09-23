package com.grasia.prima.ppi.api.exception;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void testCreateConstructor() {
        BusinessException e = new BusinessException(GlobalMessage.SUCCESS);
        assertEquals(GlobalMessage.SUCCESS.status, e.getStatus());
        assertEquals(GlobalMessage.SUCCESS.message, e.getMessage());
    }
}