package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MSystemParameter;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.SystemParameterHelper;
import com.grasia.prima.ppi.api.repository.SystemParameterRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.SystemParameterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SystemParameterServiceImpl extends AbstractCrudService implements SystemParameterService {

    private final SystemParameterRepository parameterRepository;

    @Override
    public List<SystemParameterResponse> findAll(SearchDto searchDto) {
        List<MSystemParameter> parameters = parameterRepository
                .findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return parameters.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<SystemParameterResponse> findAllPagination(SearchDto searchDto) {
        Page<MSystemParameter> parameters = parameterRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return parameters.map(this::toResponse);
    }

    @Override
    public SystemParameterResponse findById(Long id) {
        MSystemParameter systemParameter = getSystemParameterById(id);
        return toResponse(systemParameter);
    }

    @Override
    public SystemParameterResponse create(SystemParameterRequest request, HeaderRequest header) {
        MSystemParameter systemParameter = MSystemParameter.builder().build();
        setSystemParameter(systemParameter, request);
        setCreatedBy(systemParameter, header);
        setUpdatedBy(systemParameter, header);

        systemParameter = parameterRepository.save(systemParameter);
        return toResponse(systemParameter);
    }

    @Override
    public SystemParameterResponse update(Long id, SystemParameterRequest request, HeaderRequest header) {
        MSystemParameter systemParameter = getSystemParameterById(id);
        setSystemParameter(systemParameter, request);
        setUpdatedBy(systemParameter, header);

        systemParameter = parameterRepository.save(systemParameter);
        return toResponse(systemParameter);
    }

    @Override
    public MSystemParameter getSystemParameterById(Long id) {
        return parameterRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    private Specification<MSystemParameter> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MSystemParameter> spec = SpecificationHelper.stringLike(MSystemParameter.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setSystemParameter(MSystemParameter systemParameter, SystemParameterRequest request) {
        systemParameter.setName(request.getName());
    }

    private SystemParameterResponse toResponse(MSystemParameter systemParameter) {
        return SystemParameterHelper.toSystemParameterResponse(systemParameter);
    }
}
