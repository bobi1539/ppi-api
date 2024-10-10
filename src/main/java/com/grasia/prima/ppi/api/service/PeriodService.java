package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.PeriodRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MPeriod;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PeriodService {

    List<PeriodResponse> findAll(SearchDto searchDto);

    Page<PeriodResponse> findAllPagination(SearchDto searchDto);

    PeriodResponse findById(Long id);

    PeriodResponse create(PeriodRequest request, HeaderRequest header);

    PeriodResponse update(Long id, PeriodRequest request, HeaderRequest header);

    PeriodResponse delete(Long id, HeaderRequest header);

    PeriodResponse restore(Long id, HeaderRequest header);

    MPeriod getPeriodById(Long id);
}
