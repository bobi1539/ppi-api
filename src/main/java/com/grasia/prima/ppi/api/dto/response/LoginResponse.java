package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String jwt;
    private String refreshToken;
}
