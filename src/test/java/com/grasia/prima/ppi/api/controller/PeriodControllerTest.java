package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.PeriodRequest;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.PeriodService;
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

class PeriodControllerTest extends ControllerTest {

    @InjectMocks
    private PeriodController controller;

    @Mock
    private PeriodService service;

    private final PeriodRequest periodRequest = ObjectDummy.getPeriodRequest();
    private final PeriodResponse periodResponse = ObjectDummy.getPeriodResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getPeriodResponses());

        List<PeriodResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<PeriodResponse> getPeriodResponses() {
        return List.of(periodResponse, periodResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getPeriodResponsePage());

        Page<PeriodResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<PeriodResponse> getPeriodResponsePage() {
        return new PageImpl<>(getPeriodResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(periodResponse);

        PeriodResponse response = controller.findById(id);
        assertEquals(periodResponse.getId(), response.getId());
        assertEquals(periodResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(periodResponse);

        PeriodResponse response = controller.create(periodRequest, header);
        assertEquals(periodResponse.getId(), response.getId());
        assertEquals(periodResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(periodResponse);

        PeriodResponse response = controller.update(id, periodRequest, header);
        assertEquals(periodResponse.getId(), response.getId());
        assertEquals(periodResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(periodResponse);

        PeriodResponse response = controller.delete(id, header);
        assertEquals(periodResponse.getId(), response.getId());
        assertEquals(periodResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(periodResponse);

        PeriodResponse response = controller.restore(id, header);
        assertEquals(periodResponse.getId(), response.getId());
        assertEquals(periodResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }
}