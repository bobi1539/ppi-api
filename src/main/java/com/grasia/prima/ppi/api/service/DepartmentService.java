package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.DepartmentRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MDepartment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartmentService {

    List<DepartmentResponse> findAll(SearchDto searchDto);

    Page<DepartmentResponse> findAllPagination(SearchDto searchDto);

    DepartmentResponse findById(Long id);

    DepartmentResponse create(DepartmentRequest request, HeaderRequest header);

    DepartmentResponse update(Long id, DepartmentRequest request, HeaderRequest header);

    DepartmentResponse delete(Long id, HeaderRequest header);

    DepartmentResponse restore(Long id, HeaderRequest header);

    MDepartment getDepartmentById(Long id, Boolean isDeleted);
}
