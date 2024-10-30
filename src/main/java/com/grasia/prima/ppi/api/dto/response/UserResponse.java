package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserResponse extends BaseEntityResponse {
    private Long id;
    private String username;
    private String name;
    private String email;
    private LocalDateTime emailVerifiedAt;
    private Boolean isActive;
    private String photo;
    private String description;
    private UserRoleResponse userRole;

    public static UserResponse toResponse(MUser user) {
        if (Objects.isNull(user)) {
            return null;
        }
        UserResponse response = builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .emailVerifiedAt(user.getEmailVerifiedAt())
                .isActive(user.getIsActive())
                .photo(user.getPhoto())
                .description(user.getDescription())
                .userRole(UserRoleResponse.toResponse(user.getUserRole()))
                .build();
        BaseEntityResponse.setBaseEntity(response, user);
        return response;
    }
}
