package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class UserRoleHelper {

    private UserRoleHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static UserRoleResponse toUserRoleResponse(MUserRole userRole) {
        UserRoleResponse response = UserRoleResponse.builder()
                .id(userRole.getId())
                .name(userRole.getName())
                .userCount(getUserCount(userRole))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, userRole);
        return response;
    }

    private static int getUserCount(MUserRole userRole) {
        return Objects.isNull(userRole.getUsers()) ? 0 : userRole.getUsers().size();
    }
}
