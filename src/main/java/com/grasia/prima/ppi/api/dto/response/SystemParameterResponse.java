package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MSystemParameter;
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
public class SystemParameterResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private String description;

    public static SystemParameterResponse toResponse(MSystemParameter systemParameter) {
        if (Objects.isNull(systemParameter)) {
            return null;
        }
        SystemParameterResponse response = builder()
                .id(systemParameter.getId())
                .name(systemParameter.getName())
                .description(systemParameter.getDescription())
                .build();
        BaseEntityResponse.setBaseEntity(response, systemParameter);
        return response;
    }
}
