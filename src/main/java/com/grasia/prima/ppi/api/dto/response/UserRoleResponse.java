package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserRoleResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private Integer userCount;

    public static UserRoleResponse toResponse(MUserRole userRole) {
        if (Objects.isNull(userRole)) {
            return null;
        }
        UserRoleResponse response = builder()
                .id(userRole.getId())
                .name(userRole.getName())
                .userCount(getUserCount(userRole))
                .build();
        BaseEntityResponse.setBaseEntity(response, userRole);
        return response;
    }

    private static int getUserCount(MUserRole userRole) {
        return Objects.isNull(userRole.getUsers()) ? 0 : userRole.getUsers().size();
    }
}
