package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameter;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class SystemParameterHelper {

    private SystemParameterHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static SystemParameterResponse toSystemParameterResponse(MSystemParameter systemParameter) {
        SystemParameterResponse response = SystemParameterResponse.builder()
                .id(systemParameter.getId())
                .name(systemParameter.getName())
                .description(systemParameter.getDescription())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, systemParameter);
        return response;
    }
}
