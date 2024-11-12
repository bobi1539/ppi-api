package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {
    private String jwt;
    private String refreshToken;
    private List<String> routes;
}
