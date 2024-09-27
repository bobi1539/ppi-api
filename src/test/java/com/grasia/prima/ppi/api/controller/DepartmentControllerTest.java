package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.DepartmentRequest;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.DepartmentService;
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

class DepartmentControllerTest extends ControllerTest {

    @InjectMocks
    private DepartmentController controller;

    @Mock
    private DepartmentService service;

    private final DepartmentRequest departmentRequest = ObjectDummy.getDepartmentRequest();
    private final DepartmentResponse departmentResponse = ObjectDummy.getDepartmentResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getDepartmentResponses());

        List<DepartmentResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<DepartmentResponse> getDepartmentResponses() {
        return List.of(departmentResponse, departmentResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getDepartmentResponsePage());

        Page<DepartmentResponse> responses = controller.findAllPagination("", null,1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<DepartmentResponse> getDepartmentResponsePage() {
        return new PageImpl<>(getDepartmentResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(departmentResponse);

        DepartmentResponse response = controller.findById(id);
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(departmentResponse);

        DepartmentResponse response = controller.create(departmentRequest, header);
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(departmentResponse);

        DepartmentResponse response = controller.update(id, departmentRequest, header);
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(departmentResponse);

        DepartmentResponse response = controller.delete(id, header);
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(departmentResponse);

        DepartmentResponse response = controller.restore(id, header);
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }

}