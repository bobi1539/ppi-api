package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

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
}
