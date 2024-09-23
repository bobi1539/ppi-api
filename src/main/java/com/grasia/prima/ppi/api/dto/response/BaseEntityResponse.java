package com.grasia.prima.ppi.api.dto.response;

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
}
