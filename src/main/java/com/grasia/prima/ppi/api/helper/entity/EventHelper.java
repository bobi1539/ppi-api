package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.DateHelper;

import java.util.Objects;

public final class EventHelper {

    private EventHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static EventResponse toEventResponse(MEvent event) {
        if (Objects.isNull(event)) {
            return null;
        }
        EventResponse response = EventResponse.builder()
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
        BaseEntityHelper.setBaseEntityResponse(response, event);
        return response;
    }
}
