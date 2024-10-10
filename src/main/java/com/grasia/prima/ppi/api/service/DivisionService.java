package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.DivisionRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.dto.search.DivisionSearchDto;
import com.grasia.prima.ppi.api.entity.MDivision;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DivisionService {

    List<DivisionResponse> findAll(DivisionSearchDto searchDto);

    Page<DivisionResponse> findAllPagination(DivisionSearchDto searchDto);

    DivisionResponse findById(Long id);

    DivisionResponse create(DivisionRequest request, HeaderRequest header);

    DivisionResponse update(Long id, DivisionRequest request, HeaderRequest header);

    DivisionResponse delete(Long id, HeaderRequest header);

    DivisionResponse restore(Long id, HeaderRequest header);

    MDivision getDivisionById(Long id);
}
