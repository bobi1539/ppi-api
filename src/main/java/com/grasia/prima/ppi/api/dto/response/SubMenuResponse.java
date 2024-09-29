package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
}
