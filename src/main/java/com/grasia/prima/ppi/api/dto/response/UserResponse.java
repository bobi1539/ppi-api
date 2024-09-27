package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserResponse extends BaseEntityResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private LocalDate birthDate;
    private String education;
    private String graduation;
    private UserRoleResponse userRole;
    private SystemParameterListResponse gender;
}
