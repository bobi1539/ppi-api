package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.helper.DateHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class EventResponse extends BaseEntityResponse {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String cover;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String duration;

    public static EventResponse toResponse(MEvent event) {
        if (Objects.isNull(event)) {
            return null;
        }
        EventResponse response = builder()
                .id(event.getId())
                .title(event.getTitle())
                .slug(event.getSlug())
                .description(event.getDescription())
                .cover(event.getCover())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .duration(DateHelper.getElapsedTime(event.getStartDate()))
                .build();
        BaseEntityResponse.setBaseEntity(response, event);
        return response;
    }
}
