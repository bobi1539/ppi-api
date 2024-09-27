package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameter;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SystemParameterListRepository;
import com.grasia.prima.ppi.api.service.SystemParameterService;
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

class SystemParameterListServiceImplTest extends ServiceTest {

    @InjectMocks
    private SystemParameterListServiceImpl service;

    @Mock
    private SystemParameterListRepository parameterListRepository;

    @Mock
    private SystemParameterService parameterService;

    private final MSystemParameterList parameterList = ObjectDummy.getSystemParameterList();
    private final MSystemParameter parameter = parameterList.getSystemParameter();
    private final SystemParameterListRequest request = ObjectDummy.getSystemParameterListRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(parameterListRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getSystemParameterLists());

        List<SystemParameterListResponse> responses = service.findAll(parameterListSearchDto);
        assertEquals(2, responses.size());

        verify(parameterListRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MSystemParameterList> getSystemParameterLists() {
        return List.of(parameterList, parameterList);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(parameterListRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getSystemParameterListPage());

        Page<SystemParameterListResponse> responses = service.findAllPagination(parameterListSearchDto);
        assertEquals(2, responses.getTotalElements());

        verify(parameterListRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MSystemParameterList> getSystemParameterListPage() {
        return new PageImpl<>(getSystemParameterLists());
    }

    @Test
    void testFindById_Success() {
        when(parameterListRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(parameterList));

        SystemParameterListResponse response = service.findById(id);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());

        verify(parameterListRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindById_NotFound() {
        when(parameterListRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> service.findById(id));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(parameterListRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(parameterService.getSystemParameterById(id)).thenReturn(parameter);
        when(parameterListRepository.save(any())).thenReturn(parameterList);

        SystemParameterListResponse response = service.create(request, header);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());

        verify(parameterService).getSystemParameterById(id);
        verify(parameterListRepository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(parameterService.getSystemParameterById(id)).thenReturn(parameter);
        when(parameterListRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(parameterList));
        when(parameterListRepository.save(any())).thenReturn(parameterList);

        SystemParameterListResponse response = service.update(id, request, header);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());

        verify(parameterService).getSystemParameterById(id);
        verify(parameterListRepository).findByIdAndIsDeleted(id, false);
        verify(parameterListRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(parameterListRepository.findById(id)).thenReturn(Optional.of(parameterList));
        when(parameterListRepository.save(any())).thenReturn(parameterList);

        SystemParameterListResponse response = service.delete(id, header);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(parameterListRepository).findById(id);
        verify(parameterListRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        parameterList.setDeleted(true);
        when(parameterListRepository.findById(id)).thenReturn(Optional.of(parameterList));

        SystemParameterListResponse response = service.delete(id, header);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(parameterListRepository).findById(id);
        verify(parameterListRepository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(parameterListRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(parameterList));
        when(parameterListRepository.save(any())).thenReturn(parameterList);

        SystemParameterListResponse response = service.restore(id, header);
        assertEquals(parameterList.getId(), response.getId());
        assertEquals(parameterList.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(parameterListRepository).findByIdAndIsDeleted(id, true);
        verify(parameterListRepository).save(any());
    }
}