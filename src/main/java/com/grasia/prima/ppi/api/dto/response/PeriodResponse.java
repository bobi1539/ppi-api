package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MPeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class PeriodResponse extends BaseEntityResponse {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status;

    public static PeriodResponse toResponse(MPeriod period) {
        if (Objects.isNull(period)) {
            return null;
        }

        PeriodResponse response = builder()
                .id(period.getId())
                .name(period.getName())
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .status(period.getStatus())
                .build();
        BaseEntityResponse.setBaseEntity(response, period);
        return response;
    }
}
