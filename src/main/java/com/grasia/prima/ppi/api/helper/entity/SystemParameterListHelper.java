package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class SystemParameterListHelper {

    private SystemParameterListHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static SystemParameterListResponse toSystemParameterListResponse(MSystemParameterList parameterList) {
        SystemParameterListResponse response = SystemParameterListResponse.builder()
                .id(parameterList.getId())
                .name(parameterList.getName())
                .systemParameter(SystemParameterHelper.toSystemParameterResponse(parameterList.getSystemParameter()))
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, parameterList);
        return response;
    }
}
