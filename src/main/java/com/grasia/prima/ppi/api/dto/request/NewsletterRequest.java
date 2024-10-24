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
public class NewsletterRequest {

    @NotNull(message = Constant.NEWSLETTER_TITLE_REQUIRED)
    @NotEmpty(message = Constant.NEWSLETTER_TITLE_REQUIRED)
    private String title;

    @NotNull(message = Constant.NEWSLETTER_DESCRIPTION_REQUIRED)
    @NotEmpty(message = Constant.NEWSLETTER_DESCRIPTION_REQUIRED)
    private String description;

    @NotNull(message = Constant.NEWSLETTER_COVER_REQUIRED)
    private FileUploadRequest cover;

    @NotNull(message = Constant.NEWSLETTER_FILE_REQUIRED)
    private FileUploadRequest content;
}
