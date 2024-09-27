package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.CommitteeRequest;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.entity.MCommittee;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.CommitteeRepository;
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

class CommitteeServiceImplTest extends ServiceTest {

    @InjectMocks
    private CommitteeServiceImpl service;

    @Mock
    private CommitteeRepository repository;

    private final MCommittee committee = ObjectDummy.getCommittee();
    private final CommitteeRequest request = ObjectDummy.getCommitteeRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getCommittees());

        List<CommitteeResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MCommittee> getCommittees() {
        return List.of(committee, committee);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getCommitteePage());

        Page<CommitteeResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MCommittee> getCommitteePage() {
        return new PageImpl<>(getCommittees());
    }

    @Test
    void testFindById_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(committee));

        CommitteeResponse response = service.findById(id);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());

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
        when(repository.save(any())).thenReturn(committee);

        CommitteeResponse response = service.create(request, header);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());

        verify(repository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(committee));
        when(repository.save(any())).thenReturn(committee);

        CommitteeResponse response = service.update(id, request, header);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());

        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(repository.findById(id)).thenReturn(Optional.of(committee));
        when(repository.save(any())).thenReturn(committee);

        CommitteeResponse response = service.delete(id, header);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        committee.setDeleted(true);
        when(repository.findById(id)).thenReturn(Optional.of(committee));

        CommitteeResponse response = service.delete(id, header);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(repository).findById(id);
        verify(repository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(repository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(committee));
        when(repository.save(any())).thenReturn(committee);

        CommitteeResponse response = service.restore(id, header);
        assertEquals(committee.getId(), response.getId());
        assertEquals(committee.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(repository).findByIdAndIsDeleted(id, true);
        verify(repository).save(any());
    }
}