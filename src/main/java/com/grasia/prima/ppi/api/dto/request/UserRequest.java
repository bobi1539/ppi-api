package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class UserRequest {

    @NotNull(message = Constant.USERNAME_REQUIRED)
    @NotEmpty(message = Constant.USERNAME_REQUIRED)
    protected String username;

    @NotNull(message = Constant.NAME_REQUIRED)
    @NotEmpty(message = Constant.NAME_REQUIRED)
    protected String name;

    @NotNull(message = Constant.EMAIL_REQUIRED)
    @NotEmpty(message = Constant.EMAIL_REQUIRED)
    protected String email;

    @NotNull(message = Constant.IS_ACTIVE_REQUIRED)
    protected Boolean isActive;

    protected FileUploadRequest photo;

    protected String description;

    @NotNull(message = Constant.USER_ROLE_ID_REQUIRED)
    protected Long userRoleId;
}
