package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.DepartmentRequest;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.entity.MDepartment;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.DepartmentRepository;
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

class DepartmentServiceImplTest extends ServiceTest {

    @InjectMocks
    private DepartmentServiceImpl service;

    @Mock
    private DepartmentRepository repository;

    private final MDepartment department = ObjectDummy.getDepartment();
    private final DepartmentRequest request = ObjectDummy.getDepartmentRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getDepartments());

        List<DepartmentResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MDepartment> getDepartments() {
        return List.of(department, department);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getDepartmentPage());

        Page<DepartmentResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MDepartment> getDepartmentPage() {
        return new PageImpl<>(getDepartments());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(department));

        DepartmentResponse response = service.findById(id);
        assertEquals(department.getId(), response.getId());
        assertEquals(department.getName(), response.getName());

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
        when(repository.save(any())).thenReturn(department);

        DepartmentResponse response = service.create(request, header);
        assertEquals(department.getId(), response.getId());
        assertEquals(department.getName(), response.getName());

        verify(repository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(department));
        when(repository.save(any())).thenReturn(department);

        DepartmentResponse response = service.update(id, request, header);
        assertEquals(department.getId(), response.getId());
        assertEquals(department.getName(), response.getName());

        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }

    @Test
    void testDelete_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(department));
        when(repository.save(any())).thenReturn(department);

        DepartmentResponse response = service.delete(id, header);
        assertEquals(department.getId(), response.getId());
        assertEquals(department.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }
}