package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.entity.MCommittee;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class CommitteeHelper {

    private CommitteeHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static CommitteeResponse toCommitteeResponse(MCommittee committee) {
        CommitteeResponse response = CommitteeResponse.builder()
                .id(committee.getId())
                .name(committee.getName())
                .startDate(committee.getStartDate())
                .endDate(committee.getEndDate())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, committee);
        return response;
    }
}
