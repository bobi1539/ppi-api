package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;
import com.grasia.prima.ppi.api.entity.MSystemParameter;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.SystemParameterListHelper;
import com.grasia.prima.ppi.api.repository.SystemParameterListRepository;
import com.grasia.prima.ppi.api.repository.SystemParameterRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SystemParameterListServiceImpl extends AbstractCrudService implements SystemParameterListService {

    private final SystemParameterListRepository parameterListRepository;
    private final SystemParameterRepository parameterRepository;

    @Override
    public List<SystemParameterListResponse> findAll(SystemParameterListSearchDto searchDto) {
        List<MSystemParameterList> parameterLists = parameterListRepository
                .findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return parameterLists.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<SystemParameterListResponse> findAllPagination(SystemParameterListSearchDto searchDto) {
        Page<MSystemParameterList> parameterLists = parameterListRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return parameterLists.map(this::toResponse);
    }

    @Override
    public SystemParameterListResponse findById(Long id) {
        MSystemParameterList parameterList = getSystemParameterListById(id);
        return toResponse(parameterList);
    }

    @Override
    public SystemParameterListResponse create(SystemParameterListRequest request, HeaderRequest header) {
        MSystemParameterList parameterList = MSystemParameterList.builder().build();
        setSystemParameterList(parameterList, request);
        setCreatedBy(parameterList, header);
        setUpdatedBy(parameterList, header);

        parameterList = parameterListRepository.save(parameterList);
        return toResponse(parameterList);
    }

    @Override
    public SystemParameterListResponse update(Long id, SystemParameterListRequest request, HeaderRequest header) {
        MSystemParameterList parameterList = getSystemParameterListById(id);
        setSystemParameterList(parameterList, request);
        setUpdatedBy(parameterList, header);

        parameterList = parameterListRepository.save(parameterList);
        return toResponse(parameterList);
    }

    @Override
    public SystemParameterListResponse delete(Long id, HeaderRequest header) {
        MSystemParameterList parameterList = getSystemParameterListById(id);
        parameterList.setDeleted(true);
        setUpdatedBy(parameterList, header);

        parameterList = parameterListRepository.save(parameterList);
        return toResponse(parameterList);
    }

    private Specification<MSystemParameterList> getSpecificationFindAll(SystemParameterListSearchDto searchDto) {
        Specification<MSystemParameterList> spec = SpecificationHelper.stringLike(MSystemParameterList.FIELD_NAME, searchDto.getSearch());
        return spec
                .and(SpecificationHelper.entityIdEquals(MSystemParameterList.FIELD_SYSTEM_PARAMETER, searchDto.getSystemParameterId()))
                .and(getSpecificationIsDeletedFalse());
    }

    private MSystemParameterList getSystemParameterListById(Long id) {
        return parameterListRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    private void setSystemParameterList(MSystemParameterList parameterList, SystemParameterListRequest request) {
        parameterList.setName(request.getName());
        parameterList.setSystemParameter(getSystemParameterById(request.getSystemParameterId()));
    }

    private MSystemParameter getSystemParameterById(Long id) {
        return parameterRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    private SystemParameterListResponse toResponse(MSystemParameterList parameterList) {
        return SystemParameterListHelper.toSystemParameterListResponse(parameterList);
    }
}
