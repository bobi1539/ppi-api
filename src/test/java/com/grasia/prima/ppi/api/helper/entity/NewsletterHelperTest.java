package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class NewsletterHelperTest {

    @Test
    void testInstanceNewsletterHelper() throws NoSuchMethodException {
        Constructor<NewsletterHelper> constructor = NewsletterHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testToNewsletterResponse_NewsletterIsNull() {
        NewsletterResponse response = NewsletterHelper.toNewsletterResponse(null);
        assertNull(response);
    }
}