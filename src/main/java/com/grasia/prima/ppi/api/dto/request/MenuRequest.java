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
public class MenuRequest {

    @NotNull(message = Constant.MENU_NAME_REQUIRED)
    @NotEmpty(message = Constant.MENU_NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.MENU_ROUTE_REQUIRED)
    @NotEmpty(message = Constant.MENU_ROUTE_REQUIRED)
    private String route;

    @NotNull(message = Constant.MENU_ICON_REQUIRED)
    @NotEmpty(message = Constant.MENU_ICON_REQUIRED)
    private String icon;

    @NotNull(message = Constant.MENU_SEQUENCE_REQUIRED)
    private Integer sequence;
}
