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
public class SubMenuRequest {

    @NotNull(message = Constant.SUB_MENU_NAME_REQUIRED)
    @NotEmpty(message = Constant.SUB_MENU_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.SUB_MENU_ROUTE_REQUIRED)
    @NotEmpty(message = Constant.SUB_MENU_ROUTE_REQUIRED)
    private String route;

    @NotNull(message = Constant.SUB_MENU_SEQUENCE_REQUIRED)
    private Integer sequence;

    @NotNull(message = Constant.MENU_ID_REQUIRED)
    private Long menuId;
}
