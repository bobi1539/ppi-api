package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.BaseEntity;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.PageHelper;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.function.Supplier;

public abstract class AbstractCrudService {

    protected static final String FIELD_ID = "id";

    protected void setCreatedBy(BaseEntity entity, HeaderRequest header) {
        entity.setCreatedBy(header.getUserId());
        entity.setCreatedByName(header.getUserFullName());
    }

    protected void setUpdatedBy(BaseEntity entity, HeaderRequest header) {
        entity.setUpdatedBy(header.getUserId());
        entity.setUpdatedByName(header.getUserFullName());
    }

    protected Sort sortByIdAsc() {
        return PageHelper.sortByColumnAsc(FIELD_ID);
    }

    protected Sort sortByIdDesc() {
        return PageHelper.sortByColumnDesc(FIELD_ID);
    }

    protected Pageable pageableSortByIdAsc(SearchDto searchDto) {
        Sort sort = PageHelper.sortByColumnAsc(FIELD_ID);
        return PageHelper.buildPageRequest(searchDto.getPage(), searchDto.getSize(), sort);
    }

    protected Pageable pageableSortByIdDesc(SearchDto searchDto) {
        Sort sort = PageHelper.sortByColumnDesc(FIELD_ID);
        return PageHelper.buildPageRequest(searchDto.getPage(), searchDto.getSize(), sort);
    }

    protected <T> Specification<T> getSpecificationIsDeleted(Boolean isDeleted) {
        return SpecificationHelper.objectEquals(BaseEntity.FIELD_IS_DELETED, isDeleted);
    }

    protected Supplier<BusinessException> getNotFoundException() {
        return () -> new BusinessException(GlobalMessage.DATA_NOT_FOUND);
    }
}
