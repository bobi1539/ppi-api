package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class EntityHelperTest {

    @Test
    void testInstanceEntityHelper() throws NoSuchMethodException {
        Constructor<EntityHelper> constructor = EntityHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }
}