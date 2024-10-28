package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.StaffRequest;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.StaffService;
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

class StaffControllerTest extends ControllerTest {

    @InjectMocks
    private StaffController controller;

    @Mock
    private StaffService service;

    private final StaffRequest staffRequest = ObjectDummy.getStaffRequest();
    private final StaffResponse staffResponse = ObjectDummy.getStaffResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getStaffResponses());

        List<StaffResponse> responses = controller.findAll(1L, null, null, "", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<StaffResponse> getStaffResponses() {
        return List.of(staffResponse, staffResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getStaffResponsePage());

        Page<StaffResponse> responses = controller.findAllPagination(1L, null, null, "", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<StaffResponse> getStaffResponsePage() {
        return new PageImpl<>(getStaffResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(staffResponse);

        StaffResponse response = controller.findById(id);
        assertEquals(staffResponse.getId(), response.getId());
        assertEquals(staffResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(staffResponse);

        StaffResponse response = controller.create(staffRequest, header);
        assertEquals(staffResponse.getId(), response.getId());
        assertEquals(staffResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(staffResponse);

        StaffResponse response = controller.update(id, staffRequest, header);
        assertEquals(staffResponse.getId(), response.getId());
        assertEquals(staffResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(staffResponse);

        StaffResponse response = controller.delete(id, header);
        assertEquals(staffResponse.getId(), response.getId());
        assertEquals(staffResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(staffResponse);

        StaffResponse response = controller.restore(id, header);
        assertEquals(staffResponse.getId(), response.getId());
        assertEquals(staffResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }
}