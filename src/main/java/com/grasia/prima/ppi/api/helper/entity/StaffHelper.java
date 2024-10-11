package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.entity.MStaff;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class StaffHelper {

    private StaffHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static StaffResponse toStaffResponse(MStaff staff) {
        if (Objects.isNull(staff)) {
            return null;
        }

        StaffResponse response = StaffResponse.builder()
                .id(staff.getId())
                .name(staff.getName())
                .position(staff.getPosition())
                .isHead(staff.getIsHead())
                .photo(staff.getPhoto())
                .quote(staff.getQuote())
                .funFact(staff.getFunFact())
                .description(staff.getDescription())
                .jobDescription(staff.getJobDescription())
                .division(DivisionHelper.toDivisionResponse(staff.getDivision()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, staff);
        return response;
    }
}
