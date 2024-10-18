package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class SubMenuHelper {

    private SubMenuHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static SubMenuResponse toSubMenuResponse(MSubMenu subMenu) {
        SubMenuResponse response = SubMenuResponse.builder()
                .id(subMenu.getId())
                .name(subMenu.getName())
                .route(subMenu.getRoute())
                .sequence(subMenu.getSequence())
                .menuId(subMenu.getMenu().getId())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, subMenu);
        return response;
    }
}
