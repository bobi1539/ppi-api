package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class UserHelper {

    private UserHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static UserResponse toUserResponse(MUser user) {
        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .emailVerifiedAt(user.getEmailVerifiedAt())
                .isActive(user.getIsActive())
                .photo(user.getPhoto())
                .description(user.getDescription())
                .userRole(UserRoleHelper.toUserRoleResponse(user.getUserRole()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, user);
        return response;
    }
}
