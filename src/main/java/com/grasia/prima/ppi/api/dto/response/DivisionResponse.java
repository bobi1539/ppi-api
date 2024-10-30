package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MDivision;
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
public class DivisionResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private PeriodResponse period;

    public static DivisionResponse toResponse(MDivision division) {
        if (Objects.isNull(division)) {
            return null;
        }
        DivisionResponse response = builder()
                .id(division.getId())
                .name(division.getName())
                .period(PeriodResponse.toResponse(division.getPeriod()))
                .build();
        BaseEntityResponse.setBaseEntity(response, division);
        return response;
    }
}
