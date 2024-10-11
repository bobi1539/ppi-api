package com.grasia.prima.ppi.api.dto.response;

import lombok.*;
import org.springframework.http.MediaType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileResponse {
    private byte[] fileBytes;
    private String fileName;
    private MediaType mediaType;
}
