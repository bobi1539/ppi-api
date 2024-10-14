package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EventRequest {

    @NotNull(message = Constant.EVENT_TITLE_REQUIRED)
    @NotEmpty(message = Constant.EVENT_TITLE_REQUIRED)
    private String title;

    @NotNull(message = Constant.EVENT_DESCRIPTION_REQUIRED)
    @NotEmpty(message = Constant.EVENT_DESCRIPTION_REQUIRED)
    private String description;

    @NotNull(message = Constant.EVENT_COVER_REQUIRED)
    @NotEmpty(message = Constant.EVENT_COVER_REQUIRED)
    private String coverBase64;

    @NotNull(message = Constant.EVENT_COVER_REQUIRED)
    @NotEmpty(message = Constant.EVENT_COVER_REQUIRED)
    private String coverFileName;

    @NotNull(message = Constant.EVENT_START_DATE_REQUIRED)
    private LocalDate startDate;

    @NotNull(message = Constant.EVENT_END_DATE_REQUIRED)
    private LocalDate endDate;
}
