package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SystemParameterListService {

    List<SystemParameterListResponse> findAll(SystemParameterListSearchDto searchDto);

    Page<SystemParameterListResponse> findAllPagination(SystemParameterListSearchDto searchDto);

    SystemParameterListResponse findById(Long id);

    SystemParameterListResponse create(SystemParameterListRequest request, HeaderRequest header);

    SystemParameterListResponse update(Long id, SystemParameterListRequest request, HeaderRequest header);

    SystemParameterListResponse delete(Long id, HeaderRequest header);

    SystemParameterListResponse restore(Long id, HeaderRequest header);

    MSystemParameterList getSystemParameterListById(Long id);
}
