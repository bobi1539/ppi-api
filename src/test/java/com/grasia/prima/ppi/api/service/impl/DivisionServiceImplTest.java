package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.DivisionRequest;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.dto.search.DivisionSearchDto;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.entity.MPeriod;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.DivisionRepository;
import com.grasia.prima.ppi.api.service.PeriodService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DivisionServiceImplTest extends ServiceTest {

    @InjectMocks
    private DivisionServiceImpl service;

    @Mock
    private DivisionRepository repository;

    @Mock
    private PeriodService periodService;

    private final MDivision division = ObjectDummy.getDivision();
    private final MPeriod period = ObjectDummy.getPeriod();
    private final DivisionRequest request = ObjectDummy.getDivisionRequest();
    private final DivisionSearchDto searchDto = ObjectDummy.getDivisionSearchDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getDivisions());

        List<DivisionResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MDivision> getDivisions() {
        return List.of(division, division);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getDivisionPage());

        Page<DivisionResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MDivision> getDivisionPage() {
        return new PageImpl<>(getDivisions());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(division));

        DivisionResponse response = service.findById(id);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());

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
        when(periodService.getPeriodById(id)).thenReturn(period);
        when(repository.save(any())).thenReturn(division);

        DivisionResponse response = service.create(request, header);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());

        verify(periodService).getPeriodById(id);
        verify(repository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(periodService.getPeriodById(id)).thenReturn(period);
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(division));
        when(repository.save(any())).thenReturn(division);

        DivisionResponse response = service.update(id, request, header);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());

        verify(periodService).getPeriodById(id);
        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(repository.findById(id)).thenReturn(Optional.of(division));
        when(repository.save(any())).thenReturn(division);

        DivisionResponse response = service.delete(id, header);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        division.setDeleted(true);
        when(repository.findById(id)).thenReturn(Optional.of(division));

        DivisionResponse response = service.delete(id, header);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(repository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(division));
        when(repository.save(any())).thenReturn(division);

        DivisionResponse response = service.restore(id, header);
        assertEquals(division.getId(), response.getId());
        assertEquals(division.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(repository).findByIdAndIsDeleted(id, true);
        verify(repository).save(any());
    }
}