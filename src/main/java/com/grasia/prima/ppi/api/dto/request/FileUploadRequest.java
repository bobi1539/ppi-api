package com.grasia.prima.ppi.api.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileUploadRequest {
    private String fileBase64;
    private String fileName;
}
