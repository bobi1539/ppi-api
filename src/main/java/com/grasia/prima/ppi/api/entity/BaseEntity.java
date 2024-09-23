package com.grasia.prima.ppi.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created_at")
    protected Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    protected Timestamp updatedAt;

    @Column(name = "created_by")
    protected Long createdBy;

    @Column(name = "updated_by")
    protected Long updatedBy;

    @Column(name = "created_by_name")
    protected String createdByName;

    @Column(name = "updated_by_name")
    protected String updatedByName;

    @Column(name = "is_deleted")
    protected boolean isDeleted = false;

    public static final String FIELD_IS_DELETED = "isDeleted";
}
