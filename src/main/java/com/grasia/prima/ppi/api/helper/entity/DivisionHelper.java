package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class DivisionHelper {

    private DivisionHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static DivisionResponse toDivisionResponse(MDivision division) {
        if (Objects.isNull(division)) {
            return null;
        }

        DivisionResponse response = DivisionResponse.builder()
                .id(division.getId())
                .name(division.getName())
                .period(PeriodHelper.toPeriodResponse(division.getPeriod()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, division);
        return response;
    }
}
