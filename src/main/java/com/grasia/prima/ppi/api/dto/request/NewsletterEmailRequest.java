package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsletterEmailRequest {

    @Email(message = Constant.EMAIL_NOT_VALID)
    @NotNull(message = Constant.EMAIL_REQUIRED)
    @NotEmpty(message = Constant.EMAIL_REQUIRED)
    private String email;
}
