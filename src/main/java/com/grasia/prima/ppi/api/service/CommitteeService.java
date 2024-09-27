package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.CommitteeRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MCommittee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommitteeService {

    List<CommitteeResponse> findAll(SearchDto searchDto);

    Page<CommitteeResponse> findAllPagination(SearchDto searchDto);

    CommitteeResponse findById(Long id);

    CommitteeResponse create(CommitteeRequest request, HeaderRequest header);

    CommitteeResponse update(Long id, CommitteeRequest request, HeaderRequest header);

    CommitteeResponse delete(Long id, HeaderRequest header);

    CommitteeResponse restore(Long id, HeaderRequest header);

    MCommittee getCommitteeById(Long id);
}
