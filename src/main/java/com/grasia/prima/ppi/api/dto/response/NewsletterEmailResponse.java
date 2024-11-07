package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.TNewsletterEmail;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsletterEmailResponse {
    private Long id;
    private String email;

    public static NewsletterEmailResponse toResponse(TNewsletterEmail email) {
        if (Objects.isNull(email)) {
            return null;
        }
        return builder()
                .id(email.getId())
                .email(email.getEmail())
                .build();
    }
}
