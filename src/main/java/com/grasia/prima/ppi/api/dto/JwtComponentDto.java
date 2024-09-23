package com.grasia.prima.ppi.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtComponentDto {
    private String userId;
    private String username;
    private String userFullName;
}
