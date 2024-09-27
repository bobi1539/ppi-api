package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
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

class SystemParameterListControllerTest extends ControllerTest {

    @InjectMocks
    private SystemParameterListController controller;

    @Mock
    private SystemParameterListService service;
    private final SystemParameterListRequest systemParameterListRequest = ObjectDummy.getSystemParameterListRequest();
    private final SystemParameterListResponse systemParameterListResponse = ObjectDummy.getSystemParameterListResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getSystemParameterListResponses());

        List<SystemParameterListResponse> responses = controller.findAll(1L, "");
        assertEquals(2, responses.size());

        verify(service, times(1)).findAll(any());
    }

    private List<SystemParameterListResponse> getSystemParameterListResponses() {
        return List.of(systemParameterListResponse, systemParameterListResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getSystemParameterListResponsePage());

        Page<SystemParameterListResponse> responses = controller.findAllPagination(1L, "", 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service, times(1)).findAllPagination(any());
    }

    private Page<SystemParameterListResponse> getSystemParameterListResponsePage() {
        return new PageImpl<>(getSystemParameterListResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(systemParameterListResponse);

        SystemParameterListResponse response = controller.findById(id);
        assertEquals(systemParameterListResponse.getId(), response.getId());
        assertEquals(systemParameterListResponse.getName(), response.getName());

        verify(service, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(systemParameterListResponse);

        SystemParameterListResponse response = controller.create(systemParameterListRequest, header);
        assertEquals(systemParameterListResponse.getId(), response.getId());
        assertEquals(systemParameterListResponse.getName(), response.getName());

        verify(service, times(1)).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(systemParameterListResponse);

        SystemParameterListResponse response = controller.update(id, systemParameterListRequest, header);
        assertEquals(systemParameterListResponse.getId(), response.getId());
        assertEquals(systemParameterListResponse.getName(), response.getName());

        verify(service, times(1)).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(systemParameterListResponse);

        SystemParameterListResponse response = controller.delete(id, header);
        assertEquals(systemParameterListResponse.getId(), response.getId());
        assertEquals(systemParameterListResponse.getName(), response.getName());

        verify(service, times(1)).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(systemParameterListResponse);

        SystemParameterListResponse response = controller.restore(id, header);
        assertEquals(systemParameterListResponse.getId(), response.getId());
        assertEquals(systemParameterListResponse.getName(), response.getName());

        verify(service, times(1)).restore(any(), any());
    }
}