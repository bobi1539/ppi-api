package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

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
    private FileUploadRequest cover;

    @NotNull(message = Constant.EVENT_START_DATE_REQUIRED)
    private LocalDate startDate;

    @NotNull(message = Constant.EVENT_END_DATE_REQUIRED)
    private LocalDate endDate;

    @Schema(type = "string", format = "time", example = "14:30:00")
    @NotNull(message = Constant.EVENT_START_TIME_REQUIRED)
    private LocalTime startTime;

    @Schema(type = "string", format = "time", example = "14:30:00")
    @NotNull(message = Constant.EVENT_END_TIME_REQUIRED)
    private LocalTime endTime;
}
