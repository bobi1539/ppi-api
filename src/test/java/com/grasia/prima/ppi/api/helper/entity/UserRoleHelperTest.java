package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleHelperTest {

    @Test
    void testInstanceUserRoleHelper() throws NoSuchMethodException {
        Constructor<UserRoleHelper> constructor = UserRoleHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testToUserRoleResponse_UserRoleIsNull() {
        UserRoleResponse response = UserRoleHelper.toUserRoleResponse(null);
        assertNull(response);
    }

    @Test
    void testToUserRoleResponse_UsersNotNull() {
        MUserRole userRole = ObjectDummy.getUserRole();
        userRole.setUsers(List.of(ObjectDummy.getUser()));

        UserRoleResponse response = UserRoleHelper.toUserRoleResponse(userRole);
        assert response != null;
        assertEquals(1, response.getUserCount());
    }
}