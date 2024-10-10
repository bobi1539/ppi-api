package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.DivisionRequest;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.DivisionService;
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

class DivisionControllerTest extends ControllerTest {

    @InjectMocks
    private DivisionController controller;

    @Mock
    private DivisionService service;

    private final DivisionRequest divisionRequest = ObjectDummy.getDivisionRequest();
    private final DivisionResponse divisionResponse = ObjectDummy.getDivisionResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getDivisionResponses());

        List<DivisionResponse> responses = controller.findAll(1L, "", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<DivisionResponse> getDivisionResponses() {
        return List.of(divisionResponse, divisionResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getDivisionResponsePage());

        Page<DivisionResponse> responses = controller.findAllPagination(1L, "", null,1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<DivisionResponse> getDivisionResponsePage() {
        return new PageImpl<>(getDivisionResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(divisionResponse);

        DivisionResponse response = controller.findById(id);
        assertEquals(divisionResponse.getId(), response.getId());
        assertEquals(divisionResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(divisionResponse);

        DivisionResponse response = controller.create(divisionRequest, header);
        assertEquals(divisionResponse.getId(), response.getId());
        assertEquals(divisionResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(divisionResponse);

        DivisionResponse response = controller.update(id, divisionRequest, header);
        assertEquals(divisionResponse.getId(), response.getId());
        assertEquals(divisionResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(divisionResponse);

        DivisionResponse response = controller.delete(id, header);
        assertEquals(divisionResponse.getId(), response.getId());
        assertEquals(divisionResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(divisionResponse);

        DivisionResponse response = controller.restore(id, header);
        assertEquals(divisionResponse.getId(), response.getId());
        assertEquals(divisionResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }

}