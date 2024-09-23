package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.BaseEntityResponse;
import com.grasia.prima.ppi.api.entity.BaseEntity;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class BaseEntityHelper {

    private BaseEntityHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static void setBaseEntityResponse(BaseEntityResponse response, BaseEntity entity) {
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());
        response.setCreatedByName(entity.getCreatedByName());
        response.setUpdatedByName(entity.getUpdatedByName());
        response.setDeleted(entity.isDeleted());
    }
}
