package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.CommitteeRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MCommittee;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.CommitteeHelper;
import com.grasia.prima.ppi.api.repository.CommitteeRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.CommitteeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommitteeServiceImpl extends AbstractCrudService implements CommitteeService {

    private final CommitteeRepository committeeRepository;

    @Override
    public List<CommitteeResponse> findAll(SearchDto searchDto) {
        List<MCommittee> committees = committeeRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return committees.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<CommitteeResponse> findAllPagination(SearchDto searchDto) {
        Page<MCommittee> committees = committeeRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return committees.map(this::toResponse);
    }

    @Override
    public CommitteeResponse findById(Long id) {
        MCommittee committee = getCommitteeById(id);
        return toResponse(committee);
    }

    @Override
    public CommitteeResponse create(CommitteeRequest request, HeaderRequest header) {
        MCommittee committee = MCommittee.builder().build();
        setCommittee(committee, request);
        setCreatedBy(committee, header);
        setUpdatedBy(committee, header);

        return toResponse(committeeRepository.save(committee));
    }

    @Override
    public CommitteeResponse update(Long id, CommitteeRequest request, HeaderRequest header) {
        MCommittee committee = getCommitteeById(id);
        setCommittee(committee, request);
        setUpdatedBy(committee, header);

        return toResponse(committeeRepository.save(committee));
    }

    @Override
    public CommitteeResponse delete(Long id, HeaderRequest header) {
        MCommittee committee = committeeRepository.findById(id).orElseThrow(getNotFoundException());
        if (committee.isDeleted()) {
            committeeRepository.delete(committee);
        } else {
            committee.setDeleted(true);
            setUpdatedBy(committee, header);
            committee = committeeRepository.save(committee);
        }
        return toResponse(committee);
    }

    @Override
    public CommitteeResponse restore(Long id, HeaderRequest header) {
        MCommittee committee = getCommitteeDeleted(id);
        committee.setDeleted(false);
        setUpdatedBy(committee, header);

        return toResponse(committeeRepository.save(committee));
    }

    @Override
    public MCommittee getCommitteeById(Long id) {
        return committeeRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MCommittee> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MCommittee> spec = SpecificationHelper.stringLike(MCommittee.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setCommittee(MCommittee committee, CommitteeRequest request) {
        committee.setName(request.getName());
        committee.setStartDate(request.getStartDate());
        committee.setEndDate(request.getEndDate());
    }

    private MCommittee getCommitteeDeleted(Long id) {
        return committeeRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private CommitteeResponse toResponse(MCommittee committee) {
        return CommitteeHelper.toCommitteeResponse(committee);
    }
}
