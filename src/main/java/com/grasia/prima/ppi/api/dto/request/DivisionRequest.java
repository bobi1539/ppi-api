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
public class DivisionRequest {

    @NotNull(message = Constant.DIVISION_NAME_REQUIRED)
    @NotEmpty(message = Constant.DIVISION_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.PERIOD_ID_REQUIRED)
    private Long periodId;
}
