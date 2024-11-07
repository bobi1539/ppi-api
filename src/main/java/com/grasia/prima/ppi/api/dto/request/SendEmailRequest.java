package com.grasia.prima.ppi.api.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SendEmailRequest {
    private String to;
    private String subject;
    private String body;
}
