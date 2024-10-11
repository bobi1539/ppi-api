package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class PeriodHelper {

    private PeriodHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static PeriodResponse toPeriodResponse(MPeriod period) {
        if (Objects.isNull(period)) {
            return null;
        }

        PeriodResponse response = PeriodResponse.builder()
                .id(period.getId())
                .name(period.getName())
                .startDate(period.getStartDate())
                .endDate(period.getEndDate())
                .status(period.getStatus())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, period);
        return response;
    }
}
