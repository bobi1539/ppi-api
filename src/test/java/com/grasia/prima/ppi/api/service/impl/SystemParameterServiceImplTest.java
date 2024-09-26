package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameter;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SystemParameterRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SystemParameterServiceImplTest extends ServiceTest {

    @InjectMocks
    private SystemParameterServiceImpl service;

    @Mock
    private SystemParameterRepository repository;

    private final MSystemParameter systemParameter = ObjectDummy.getSystemParameter();
    private final SystemParameterRequest request = ObjectDummy.getSystemParameterRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getSystemParameters());

        List<SystemParameterResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MSystemParameter> getSystemParameters() {
        return List.of(systemParameter, systemParameter);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getSystemParameterPage());

        Page<SystemParameterResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MSystemParameter> getSystemParameterPage() {
        return new PageImpl<>(getSystemParameters());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(systemParameter));

        SystemParameterResponse response = service.findById(id);
        assertEquals(systemParameter.getId(), response.getId());
        assertEquals(systemParameter.getName(), response.getName());

        verify(repository, times(1)).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindById_NotFound() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> service.findById(id));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(repository, times(1)).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(repository.save(any())).thenReturn(systemParameter);

        SystemParameterResponse response = service.create(request, header);
        assertEquals(systemParameter.getId(), response.getId());
        assertEquals(systemParameter.getName(), response.getName());

        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(systemParameter));
        when(repository.save(any())).thenReturn(systemParameter);

        SystemParameterResponse response = service.update(id, request, header);
        assertEquals(systemParameter.getId(), response.getId());
        assertEquals(systemParameter.getName(), response.getName());

        verify(repository, times(1)).findByIdAndIsDeleted(id, false);
        verify(repository, times(1)).save(any());
    }

}