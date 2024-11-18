package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentEducationResponse {
    private String name;
    private long count;
}
