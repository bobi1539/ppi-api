package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GalleryRequest {

    @NotNull(message = Constant.EVENT_ID_REQUIRED)
    private Long eventId;

    @NotNull(message = Constant.GALLERY_FILE_REQUIRED)
    @NotEmpty(message = Constant.GALLERY_FILE_REQUIRED)
    private List<FileUploadRequest> fileUploads;
}
