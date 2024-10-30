package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.DivisionRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.dto.search.DivisionSearchDto;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.helper.PageHelper;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.repository.DivisionRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.DivisionService;
import com.grasia.prima.ppi.api.service.PeriodService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DivisionServiceImpl extends AbstractCrudService implements DivisionService {

    private final DivisionRepository divisionRepository;
    private final PeriodService periodService;

    @Override
    public List<DivisionResponse> findAll(DivisionSearchDto searchDto) {
        List<MDivision> divisions = divisionRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAndPeriodIdAsc());
        return divisions.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<DivisionResponse> findAllPagination(DivisionSearchDto searchDto) {
        Page<MDivision> divisions = divisionRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdAndPeriodIdAsc(searchDto));
        return divisions.map(this::toResponse);
    }

    @Override
    public DivisionResponse findById(Long id) {
        MDivision division = getDivisionById(id);
        return toResponse(division);
    }

    @Transactional
    @Override
    public DivisionResponse create(DivisionRequest request, HeaderRequest header) {
        MDivision division = MDivision.builder().build();
        setDivision(division, request);
        setCreatedBy(division, header);
        setUpdatedBy(division, header);

        division = divisionRepository.save(division);
        return toResponse(division);
    }

    @Transactional
    @Override
    public DivisionResponse update(Long id, DivisionRequest request, HeaderRequest header) {
        MDivision division = getDivisionById(id);
        setDivision(division, request);
        setUpdatedBy(division, header);

        division = divisionRepository.save(division);
        return toResponse(division);
    }

    @Transactional
    @Override
    public DivisionResponse delete(Long id, HeaderRequest header) {
        MDivision division = divisionRepository.findById(id).orElseThrow(getNotFoundException());
        if (division.isDeleted()) {
            divisionRepository.delete(division);
        } else {
            division.setDeleted(true);
            setUpdatedBy(division, header);
            division = divisionRepository.save(division);
        }
        return toResponse(division);
    }

    @Transactional
    @Override
    public DivisionResponse restore(Long id, HeaderRequest header) {
        MDivision division = getDivisionDeleted(id);
        division.setDeleted(false);
        setUpdatedBy(division, header);

        division = divisionRepository.save(division);
        return toResponse(division);
    }

    @Override
    public MDivision getDivisionById(Long id) {
        return divisionRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MDivision> getSpecificationFindAll(DivisionSearchDto searchDto) {
        Specification<MDivision> spec = SpecificationHelper.stringLike(MDivision.FIELD_NAME, searchDto.getSearch());
        return spec
                .and(SpecificationHelper.entityIdEquals(MDivision.FIELD_PERIOD, searchDto.getPeriodId()))
                .and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private Sort sortByIdAndPeriodIdAsc() {
        return Sort.by(MDivision.FIELD_PERIOD_ID).and(Sort.by(MDivision.FIELD_ID));
    }

    private Pageable pageableSortByIdAndPeriodIdAsc(DivisionSearchDto searchDto) {
        Sort sort = sortByIdAndPeriodIdAsc();
        return PageHelper.buildPageRequest(searchDto.getPage(), searchDto.getSize(), sort);
    }

    private void setDivision(MDivision division, DivisionRequest request) {
        division.setName(request.getName());
        division.setPeriod(getPeriodById(request.getPeriodId()));
    }

    private MPeriod getPeriodById(Long id) {
        return periodService.getPeriodById(id);
    }

    private MDivision getDivisionDeleted(Long id) {
        return divisionRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private DivisionResponse toResponse(MDivision division) {
        return DivisionResponse.toResponse(division);
    }
}
