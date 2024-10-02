package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRoleMenuResponse {
    private Long userRoleId;
    private String name;
    private List<MenuResponse> menus;
}
