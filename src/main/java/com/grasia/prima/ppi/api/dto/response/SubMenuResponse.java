package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MSubMenu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SubMenuResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private String route;
    private Integer sequence;
    private Long menuId;

    public static SubMenuResponse toResponse(MSubMenu subMenu) {
        if (Objects.isNull(subMenu)) {
            return null;
        }
        SubMenuResponse response = builder()
                .id(subMenu.getId())
                .name(subMenu.getName())
                .route(subMenu.getRoute())
                .sequence(subMenu.getSequence())
                .menuId(subMenu.getMenu().getId())
                .build();
        BaseEntityResponse.setBaseEntity(response, subMenu);
        return response;
    }
}
