package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MSystemParameterList;
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
public class SystemParameterListResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private SystemParameterResponse systemParameter;

    public static SystemParameterListResponse toResponse(MSystemParameterList parameterList) {
        if (Objects.isNull(parameterList)) {
            return null;
        }
        SystemParameterListResponse response = builder()
                .id(parameterList.getId())
                .name(parameterList.getName())
                .systemParameter(SystemParameterResponse.toResponse(parameterList.getSystemParameter()))
                .build();
        BaseEntityResponse.setBaseEntity(response, parameterList);
        return response;
    }
}
