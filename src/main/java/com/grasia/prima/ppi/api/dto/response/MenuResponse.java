package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class MenuResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private String route;
    private String icon;
    private Integer sequence;
    private List<SubMenuResponse> subMenus;

    public static MenuResponse toResponse(MMenu menu) {
        if (Objects.isNull(menu)) {
            return null;
        }
        MenuResponse response = builder()
                .id(menu.getId())
                .name(menu.getName())
                .route(menu.getRoute())
                .icon(menu.getIcon())
                .sequence(menu.getSequence())
                .subMenus(getSubMenus(menu.getSubMenus()))
                .build();
        BaseEntityResponse.setBaseEntity(response, menu);
        return response;
    }

    private static List<SubMenuResponse> getSubMenus(List<MSubMenu> subMenus) {
        if (Objects.isNull(subMenus)) {
            return Collections.emptyList();
        }
        return subMenus.stream().map(SubMenuResponse::toResponse).toList();
    }
}
