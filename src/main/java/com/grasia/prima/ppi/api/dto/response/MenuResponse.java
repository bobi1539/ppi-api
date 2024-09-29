package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
}
