package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class UserHelper {

    private UserHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static UserResponse toUserResponse(MUser user) {
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .education(user.getEducation())
                .graduation(user.getGraduation())
                .userRole(getUserRole(user.getUserRole()))
                .gender(getParameterListResponse(user.getGender()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, user);
        return response;
    }

    private static UserRoleResponse getUserRole(MUserRole userRole) {
        return UserRoleHelper.toUserRoleResponse(userRole);
    }

    private static SystemParameterListResponse getParameterListResponse(MSystemParameterList parameterList) {
        return SystemParameterListHelper.toSystemParameterListResponse(parameterList);
    }
}
