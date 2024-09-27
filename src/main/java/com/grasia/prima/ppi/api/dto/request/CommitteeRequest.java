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
public class CommitteeRequest {

    @NotNull(message = Constant.COMMITTEE_NAME_REQUIRED)
    @NotEmpty(message = Constant.COMMITTEE_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.START_DATE_REQUIRED)
    private LocalDate startDate;

    @NotNull(message = Constant.END_DATE_REQUIRED)
    private LocalDate endDate;
}
