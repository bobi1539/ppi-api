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
public class StaffRequest {

    @NotNull(message = Constant.STAFF_NAME_REQUIRED)
    @NotEmpty(message = Constant.STAFF_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.STAFF_POSITION_REQUIRED)
    @NotEmpty(message = Constant.STAFF_POSITION_REQUIRED)
    private String position;

    @NotNull(message = Constant.STAFF_IS_HEAD_REQUIRED)
    private Boolean isHead;

    private String photoBase64;

    @NotNull(message = Constant.PHOTO_FILE_NAME_REQUIRED)
    @NotEmpty(message = Constant.PHOTO_FILE_NAME_REQUIRED)
    private String photoFileName;

    private String quote;

    private String funFact;

    private String description;

    private String jobDescription;

    @NotNull(message = Constant.DIVISION_ID_REQUIRED)
    private Long divisionId;
}
