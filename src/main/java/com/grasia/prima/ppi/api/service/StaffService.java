package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StaffRequest;
import com.grasia.prima.ppi.api.dto.response.StaffDivisionResponse;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.dto.search.StaffSearchDto;
import com.grasia.prima.ppi.api.entity.MStaff;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {

    List<StaffResponse> findAll(StaffSearchDto searchDto);

    Page<StaffResponse> findAllPagination(StaffSearchDto searchDto);

    StaffResponse findById(Long id);

    StaffResponse create(StaffRequest request, HeaderRequest header);

    StaffResponse update(Long id, StaffRequest request, HeaderRequest header);

    StaffResponse delete(Long id, HeaderRequest header);

    StaffResponse restore(Long id, HeaderRequest header);

    MStaff getStaffById(Long id);

    List<StaffDivisionResponse> findByPeriodId(Long periodId);
}
