package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class UserRequest {

    @NotNull(message = Constant.USERNAME_REQUIRED)
    @NotEmpty(message = Constant.USERNAME_REQUIRED)
    protected String username;

    @NotNull(message = Constant.FULL_NAME_REQUIRED)
    @NotEmpty(message = Constant.FULL_NAME_REQUIRED)
    protected String fullName;

    @NotNull(message = Constant.EMAIL_REQUIRED)
    @NotEmpty(message = Constant.EMAIL_REQUIRED)
    protected String email;

    @NotNull(message = Constant.BIRTH_DATE_REQUIRED)
    protected LocalDate birthDate;

    @NotNull(message = Constant.EDUCATION_REQUIRED)
    @NotEmpty(message = Constant.EDUCATION_REQUIRED)
    protected String education;

    @NotNull(message = Constant.GRADUATION_REQUIRED)
    @NotEmpty(message = Constant.GRADUATION_REQUIRED)
    protected String graduation;

    @NotNull(message = Constant.USER_ROLE_ID_REQUIRED)
    protected Long userRoleId;

    @NotNull(message = Constant.GENDER_ID_REQUIRED)
    protected Long genderId;
}
