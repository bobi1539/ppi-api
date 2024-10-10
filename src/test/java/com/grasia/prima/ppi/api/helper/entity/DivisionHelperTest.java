package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class DivisionHelperTest {

    @Test
    void testInstanceDivisionHelper() throws NoSuchMethodException {
        Constructor<DivisionHelper> constructor = DivisionHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testToDivisionResponse_PeriodIsNull() {
        MDivision division = ObjectDummy.getDivision();
        division.setPeriod(null);

        DivisionResponse response = DivisionHelper.toDivisionResponse(division);
        assertNull(response.getPeriod());
    }
}