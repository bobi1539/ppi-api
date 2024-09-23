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
public class LoginRequest {

    @NotNull(message = Constant.USERNAME_REQUIRED)
    @NotEmpty(message = Constant.USERNAME_REQUIRED)
    private String username;

    @NotNull(message = Constant.PASSWORD_REQUIRED)
    @NotEmpty(message = Constant.PASSWORD_REQUIRED)
    private String password;
}
