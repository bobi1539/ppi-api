package com.grasia.prima.ppi.api.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Base64ToFileDto {
    private String directoryName;
    private String fileName;
    private String base64String;
}
