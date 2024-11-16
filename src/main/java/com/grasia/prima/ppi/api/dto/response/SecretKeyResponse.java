package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.TSecretKey;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SecretKeyResponse {
    private Long id;
    private String name;
    private String key;
    private LocalDateTime validDate;
    private Timestamp createdAt;

    public static SecretKeyResponse toResponse(TSecretKey secretKey) {
        if (Objects.isNull(secretKey)) {
            return null;
        }
        return builder()
                .id(secretKey.getId())
                .name(secretKey.getName())
                .key(secretKey.getKey())
                .validDate(secretKey.getValidDate())
                .createdAt(secretKey.getCreatedAt())
                .build();
    }
}
