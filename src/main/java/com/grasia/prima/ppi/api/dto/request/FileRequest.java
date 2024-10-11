package com.grasia.prima.ppi.api.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileRequest {
    private String directoryName;
    private String fileName;
    private byte[] fileBytes;
}
