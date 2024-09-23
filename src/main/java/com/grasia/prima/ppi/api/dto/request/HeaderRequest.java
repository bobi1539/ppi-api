package com.grasia.prima.ppi.api.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class HeaderRequest {
    private Long userId;
    private String userFullName;

    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_USER_FULL_NAME = "userFullName";
}
