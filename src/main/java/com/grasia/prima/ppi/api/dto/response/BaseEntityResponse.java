package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class BaseEntityResponse {
    protected Timestamp createdAt;
    protected Timestamp updatedAt;
    protected Long createdBy;
    protected Long updatedBy;
    protected String createdByName;
    protected String updatedByName;
    protected boolean isDeleted;

    public static void setBaseEntity(BaseEntityResponse response, BaseEntity entity) {
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        response.setUpdatedBy(entity.getUpdatedBy());
        response.setCreatedByName(entity.getCreatedByName());
        response.setUpdatedByName(entity.getUpdatedByName());
        response.setDeleted(entity.isDeleted());
    }
}
