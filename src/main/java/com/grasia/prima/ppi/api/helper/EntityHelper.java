package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.dto.response.BaseEntityResponse;
import com.grasia.prima.ppi.api.entity.BaseEntity;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;

public final class EntityHelper {

    private EntityHelper() {
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
