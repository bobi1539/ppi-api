package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.entity.BaseEntity;
import com.grasia.prima.ppi.api.helper.PageHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public abstract class AbstractCrudService {

    protected static final String FIELD_ID = "id";

    protected void setCreatedBy(BaseEntity entity, HeaderRequest header) {
        entity.setCreatedBy(header.getUserId());
        entity.setCreatedByName(header.getUserName());
    }

    protected void setUpdatedBy(BaseEntity entity, HeaderRequest header) {
        entity.setUpdatedBy(header.getUserId());
        entity.setUpdatedByName(header.getUserName());
    }

    protected Sort sortByIdAsc() {
        return PageHelper.sortByColumnAsc(FIELD_ID);
    }

    protected Pageable pageableSortByIdAsc(PageDto pageDto) {
        Sort sort = PageHelper.sortByColumnAsc(FIELD_ID);
        return PageHelper.buildPageRequest(pageDto.getPage(), pageDto.getSize(), sort);
    }
}
