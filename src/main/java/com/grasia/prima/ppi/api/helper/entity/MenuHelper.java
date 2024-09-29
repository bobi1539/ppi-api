package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class MenuHelper {

    private MenuHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static MenuResponse toMenuResponse(MMenu menu) {
        MenuResponse response = MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .route(menu.getRoute())
                .icon(menu.getIcon())
                .sequence(menu.getSequence())
                .subMenus(getSubMenus(menu.getSubMenus()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, menu);
        return response;
    }

    private static List<SubMenuResponse> getSubMenus(List<MSubMenu> subMenus) {
        if (Objects.isNull(subMenus)) {
            return Collections.emptyList();
        }
        return subMenus.stream().map(SubMenuHelper::toSubMenuResponse).toList();
    }
}
