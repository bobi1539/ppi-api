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
public class UserRoleMenuRequest {

    @NotNull(message = Constant.USER_ROLE_ID_REQUIRED)
    private Long userRoleId;

    @NotNull(message = Constant.MENU_ID_REQUIRED)
    private List<UserRoleSubMenuRequest> menuIds;
}
