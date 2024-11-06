package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @NotNull(message = Constant.PASSWORD_REQUIRED)
    @NotEmpty(message = Constant.PASSWORD_REQUIRED)
    private String password;

    @NotNull(message = Constant.PASSWORD_CONFIRM_REQUIRED)
    @NotEmpty(message = Constant.PASSWORD_CONFIRM_REQUIRED)
    private String passwordConfirm;
}
