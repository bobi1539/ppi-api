package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.SystemParameterService;
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
import static org.mockito.Mockito.*;

class SystemParameterControllerTest extends ControllerTest {

    @InjectMocks
    private SystemParameterController controller;

    @Mock
    private SystemParameterService service;
    private final SystemParameterRequest systemParameterRequest = ObjectDummy.getSystemParameterRequest();
    private final SystemParameterResponse systemParameterResponse = ObjectDummy.getSystemParameterResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getSystemParameterResponses());

        List<SystemParameterResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service, times(1)).findAll(any());
    }

    private List<SystemParameterResponse> getSystemParameterResponses() {
        return List.of(systemParameterResponse, systemParameterResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getSystemParameterResponsePage());

        Page<SystemParameterResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service, times(1)).findAllPagination(any());
    }

    private Page<SystemParameterResponse> getSystemParameterResponsePage() {
        return new PageImpl<>(getSystemParameterResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(systemParameterResponse);

        SystemParameterResponse response = controller.findById(id);
        assertEquals(systemParameterResponse.getId(), response.getId());
        assertEquals(systemParameterResponse.getName(), response.getName());

        verify(service, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(systemParameterResponse);

        SystemParameterResponse response = controller.create(systemParameterRequest, header);
        assertEquals(systemParameterResponse.getId(), response.getId());
        assertEquals(systemParameterResponse.getName(), response.getName());

        verify(service, times(1)).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(systemParameterResponse);

        SystemParameterResponse response = controller.update(id, systemParameterRequest, header);
        assertEquals(systemParameterResponse.getId(), response.getId());
        assertEquals(systemParameterResponse.getName(), response.getName());

        verify(service, times(1)).update(any(), any(), any());
    }
}