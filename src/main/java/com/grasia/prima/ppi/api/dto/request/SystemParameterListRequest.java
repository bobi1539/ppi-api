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
public class SystemParameterListRequest {

    @NotNull(message = Constant.SYSTEM_PARAMETER_LIST_NAME_REQUIRED)
    @NotEmpty(message = Constant.SYSTEM_PARAMETER_LIST_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.SYSTEM_PARAMETER_ID_REQUIRED)
    private Long systemParameterId;
}
