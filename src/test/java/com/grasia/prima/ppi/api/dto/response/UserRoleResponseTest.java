package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleResponseTest {

    @Test
    void testToResponse_UserRoleIsNull() {
        assertNull(UserRoleResponse.toResponse(null));
    }

    @Test
    void testToResponse_UserNotNull() {
        MUserRole userRole = ObjectDummy.getUserRole();
        userRole.setUsers(List.of(ObjectDummy.getUser()));

        UserRoleResponse response = UserRoleResponse.toResponse(userRole);
        assert response != null;
        assertEquals(1, response.getUserCount());
    }
}