package com.grasia.prima.ppi.api.dto.request;

import com.grasia.prima.ppi.api.constant.Constant;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRoleSubMenuRequest {

    @NotNull(message = Constant.MENU_ID_REQUIRED)
    private Long menuId;

    private List<Long> subMenuIds;
}
