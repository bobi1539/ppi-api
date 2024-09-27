package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.CommitteeRequest;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.CommitteeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommitteeControllerTest extends ControllerTest {

    @InjectMocks
    private CommitteeController controller;

    @Mock
    private CommitteeService service;

    private final CommitteeRequest committeeRequest = ObjectDummy.getCommitteeRequest();
    private final CommitteeResponse committeeResponse = ObjectDummy.getCommitteeResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getCommitteeResponses());

        List<CommitteeResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<CommitteeResponse> getCommitteeResponses() {
        return List.of(committeeResponse, committeeResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getCommitteeResponsePage());

        Page<CommitteeResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<CommitteeResponse> getCommitteeResponsePage() {
        return new PageImpl<>(getCommitteeResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(committeeResponse);

        CommitteeResponse response = controller.findById(id);
        assertEquals(committeeResponse.getId(), response.getId());
        assertEquals(committeeResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(committeeResponse);

        CommitteeResponse response = controller.create(committeeRequest, header);
        assertEquals(committeeResponse.getId(), response.getId());
        assertEquals(committeeResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(committeeResponse);

        CommitteeResponse response = controller.update(id, committeeRequest, header);
        assertEquals(committeeResponse.getId(), response.getId());
        assertEquals(committeeResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(committeeResponse);

        CommitteeResponse response = controller.delete(id, header);
        assertEquals(committeeResponse.getId(), response.getId());
        assertEquals(committeeResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(committeeResponse);

        CommitteeResponse response = controller.restore(id, header);
        assertEquals(committeeResponse.getId(), response.getId());
        assertEquals(committeeResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }
}