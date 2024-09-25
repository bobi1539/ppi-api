package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class StringHelperTest {

    @Test
    void testInstanceStringHelper() throws NoSuchMethodException {
        Constructor<StringHelper> constructor = StringHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testIsEmpty_NullString() {
        boolean isEmpty = StringHelper.isEmpty(null);
        assertTrue(isEmpty);
    }

    @Test
    void testIsEmpty_EmptyString() {
        boolean isEmpty = StringHelper.isEmpty("");
        assertTrue(isEmpty);
    }

    @Test
    void testIsEmpty_NotEmptyString() {
        boolean isEmpty = StringHelper.isEmpty("hello");
        assertFalse(isEmpty);
    }

    @Test
    void testQueryLike() {
        String string = StringHelper.queryLike("joko");
        assertEquals("%joko%", string);
    }

    @Test
    void testRandom() {
        String random = StringHelper.random();
        System.out.println(random);
        assertNotNull(random);
        assertEquals(20, random.length());
    }
}