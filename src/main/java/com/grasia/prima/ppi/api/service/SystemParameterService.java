package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SystemParameterService {

    List<SystemParameterResponse> findAll(SearchDto searchDto);

    Page<SystemParameterResponse> findAllPagination(PageDto pageDto);

    SystemParameterResponse findById(Long id);

    SystemParameterResponse create(SystemParameterRequest request, HeaderRequest header);

    SystemParameterResponse update(Long id, SystemParameterRequest request, HeaderRequest header);
}
