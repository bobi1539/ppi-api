package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.UserRoleRepository;
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
import static org.mockito.Mockito.*;

class UserRoleServiceImplTest extends ServiceTest {

    @InjectMocks
    private UserRoleServiceImpl service;

    @Mock
    private UserRoleRepository repository;

    private final MUserRole userRole = ObjectDummy.getUserRole();
    private final UserRoleRequest request = ObjectDummy.getUserRoleRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getUserRoles());

        List<UserRoleResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository, times(1)).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MUserRole> getUserRoles() {
        return List.of(userRole, userRole);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getUserRolePage());

        Page<UserRoleResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MUserRole> getUserRolePage() {
        return new PageImpl<>(getUserRoles());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(userRole));

        UserRoleResponse response = service.findById(id);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());

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
        when(repository.save(any())).thenReturn(userRole);

        UserRoleResponse response = service.create(request, header);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());

        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(userRole));
        when(repository.save(any())).thenReturn(userRole);

        UserRoleResponse response = service.update(id, request, header);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());

        verify(repository, times(1)).findByIdAndIsDeleted(id, false);
        verify(repository, times(1)).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(repository.findById(id)).thenReturn(Optional.of(userRole));
        when(repository.save(any())).thenReturn(userRole);

        UserRoleResponse response = service.delete(id, header);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        userRole.setDeleted(true);
        when(repository.findById(id)).thenReturn(Optional.of(userRole));

        UserRoleResponse response = service.delete(id, header);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(repository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(userRole));
        when(repository.save(any())).thenReturn(userRole);

        UserRoleResponse response = service.restore(id, header);
        assertEquals(userRole.getId(), response.getId());
        assertEquals(userRole.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(repository).findByIdAndIsDeleted(id, true);
        verify(repository).save(any());
    }
}