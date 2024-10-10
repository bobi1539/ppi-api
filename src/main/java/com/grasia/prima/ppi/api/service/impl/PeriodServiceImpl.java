package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.PeriodRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.PeriodHelper;
import com.grasia.prima.ppi.api.repository.PeriodRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PeriodServiceImpl extends AbstractCrudService implements PeriodService {

    private final PeriodRepository periodRepository;

    @Override
    public List<PeriodResponse> findAll(SearchDto searchDto) {
        List<MPeriod> periods = periodRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return periods.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<PeriodResponse> findAllPagination(SearchDto searchDto) {
        Page<MPeriod> periods = periodRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return periods.map(this::toResponse);
    }

    @Override
    public PeriodResponse findById(Long id) {
        MPeriod period = getPeriodById(id);
        return toResponse(period);
    }

    @Override
    public PeriodResponse create(PeriodRequest request, HeaderRequest header) {
        MPeriod period = MPeriod.builder().build();
        setPeriod(period, request);
        setCreatedBy(period, header);
        setUpdatedBy(period, header);

        return toResponse(periodRepository.save(period));
    }

    @Override
    public PeriodResponse update(Long id, PeriodRequest request, HeaderRequest header) {
        MPeriod period = getPeriodById(id);
        setPeriod(period, request);
        setUpdatedBy(period, header);

        return toResponse(periodRepository.save(period));
    }

    @Override
    public PeriodResponse delete(Long id, HeaderRequest header) {
        MPeriod period = periodRepository.findById(id).orElseThrow(getNotFoundException());
        if (period.isDeleted()) {
            periodRepository.delete(period);
        } else {
            period.setDeleted(true);
            setUpdatedBy(period, header);
            period = periodRepository.save(period);
        }
        return toResponse(period);
    }

    @Override
    public PeriodResponse restore(Long id, HeaderRequest header) {
        MPeriod period = getPeriodDeleted(id);
        period.setDeleted(false);
        setUpdatedBy(period, header);

        return toResponse(periodRepository.save(period));
    }

    @Override
    public MPeriod getPeriodById(Long id) {
        return periodRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MPeriod> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MPeriod> spec = SpecificationHelper.stringLike(MPeriod.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setPeriod(MPeriod period, PeriodRequest request) {
        validatePeriodRequest(request);

        period.setName(request.getName());
        period.setStartDate(request.getStartDate());
        period.setEndDate(request.getEndDate());
        period.setStatus(request.getStatus());
    }

    private void validatePeriodRequest(PeriodRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BusinessException(GlobalMessage.START_END_DATE_NOT_VALID);
        }
    }

    private MPeriod getPeriodDeleted(Long id) {
        return periodRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private PeriodResponse toResponse(MPeriod period) {
        return PeriodHelper.toPeriodResponse(period);
    }
}
