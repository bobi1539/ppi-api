package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GalleryRequest {

    @NotNull(message = Constant.GALLERY_FILE_REQUIRED)
    @NotEmpty(message = Constant.GALLERY_FILE_REQUIRED)
    private String fileBase64;

    @NotNull(message = Constant.GALLERY_FILE_REQUIRED)
    @NotEmpty(message = Constant.GALLERY_FILE_REQUIRED)
    private String fileName;

    @NotNull(message = Constant.EVENT_ID_REQUIRED)
    private Long eventId;
}
