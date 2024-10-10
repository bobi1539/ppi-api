package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.PeriodRequest;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.PeriodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PeriodServiceImplTest extends ServiceTest {

    @InjectMocks
    private PeriodServiceImpl service;

    @Mock
    private PeriodRepository repository;

    private final MPeriod period = ObjectDummy.getPeriod();
    private final PeriodRequest request = ObjectDummy.getPeriodRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getPeriods());

        List<PeriodResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MPeriod> getPeriods() {
        return List.of(period, period);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getPeriodPage());

        Page<PeriodResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MPeriod> getPeriodPage() {
        return new PageImpl<>(getPeriods());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(period));

        PeriodResponse response = service.findById(id);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());

        verify(repository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> service.findById(id));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(repository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(repository.save(any())).thenReturn(period);

        PeriodResponse response = service.create(request, header);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());

        verify(repository).save(any());
    }

    @Test
    void testCreate_StartEndDateNotValid() {
        request.setStartDate(LocalDate.of(2024, 10, 10));
        request.setEndDate(LocalDate.of(2024, 9, 10));

        BusinessException e = assertThrows(BusinessException.class, () -> service.create(request, header));
        assertEquals(GlobalMessage.START_END_DATE_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.START_END_DATE_NOT_VALID.message, e.getMessage());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(period));
        when(repository.save(any())).thenReturn(period);

        PeriodResponse response = service.update(id, request, header);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());

        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(repository.findById(id)).thenReturn(Optional.of(period));
        when(repository.save(any())).thenReturn(period);

        PeriodResponse response = service.delete(id, header);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        period.setDeleted(true);
        when(repository.findById(id)).thenReturn(Optional.of(period));

        PeriodResponse response = service.delete(id, header);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(repository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(period));
        when(repository.save(any())).thenReturn(period);

        PeriodResponse response = service.restore(id, header);
        assertEquals(period.getId(), response.getId());
        assertEquals(period.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(repository).findByIdAndIsDeleted(id, true);
        verify(repository).save(any());
    }
}