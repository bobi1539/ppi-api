package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.TNewsletterSubscription;
import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NewsletterSubscriptionResponse {
    private Long id;
    private String email;

    public static NewsletterSubscriptionResponse toResponse(TNewsletterSubscription subscription) {
        if (Objects.isNull(subscription)) {
            return null;
        }
        return builder()
                .id(subscription.getId())
                .email(subscription.getEmail())
                .build();
    }
}
