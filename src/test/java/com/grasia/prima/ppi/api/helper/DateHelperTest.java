package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateHelperTest {

    @Test
    void testInstanceDateHelper() throws NoSuchMethodException {
        Constructor<DateHelper> constructor = DateHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testGetElapsedTime_YearsAgo() {
        LocalDate startDate = LocalDate.now().minusYears(3);
        String elapsedTime = DateHelper.getElapsedTime(startDate);
        assertEquals("3 years ago", elapsedTime);
    }

    @Test
    void testGetElapsedTime_MonthsAgo() {
        LocalDate startDate = LocalDate.now().minusMonths(3);
        String elapsedTime = DateHelper.getElapsedTime(startDate);
        assertEquals("3 months ago", elapsedTime);
    }

    @Test
    void testGetElapsedTime_DaysAgo() {
        LocalDate startDate = LocalDate.now().minusDays(3);
        String elapsedTime = DateHelper.getElapsedTime(startDate);
        assertEquals("3 days ago", elapsedTime);
    }

    @Test
    void testGetElapsedTime_Today() {
        String elapsedTime = DateHelper.getElapsedTime(LocalDate.now());
        assertEquals("today", elapsedTime);
    }

    @Test
    void testGetElapsedTime_YearsFromNow() {
        LocalDate startDate = LocalDate.now().plusYears(3);
        String elapsedTime = DateHelper.getElapsedTime(startDate);
        assertEquals("3 years from now", elapsedTime);
    }
}