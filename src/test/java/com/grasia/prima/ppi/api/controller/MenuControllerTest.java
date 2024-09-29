package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.MenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.MenuService;
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

class MenuControllerTest extends ControllerTest {

    @InjectMocks
    private MenuController controller;

    @Mock
    private MenuService service;

    private final MenuRequest menuRequest = ObjectDummy.getMenuRequest();
    private final MenuResponse menuResponse = ObjectDummy.getMenuResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getMenuResponses());

        List<MenuResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<MenuResponse> getMenuResponses() {
        return List.of(menuResponse, menuResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getMenuResponsePage());

        Page<MenuResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<MenuResponse> getMenuResponsePage() {
        return new PageImpl<>(getMenuResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(menuResponse);

        MenuResponse response = controller.findById(id);
        assertEquals(menuResponse.getId(), response.getId());
        assertEquals(menuResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(menuResponse);

        MenuResponse response = controller.create(menuRequest, header);
        assertEquals(menuResponse.getId(), response.getId());
        assertEquals(menuResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(menuResponse);

        MenuResponse response = controller.update(id, menuRequest, header);
        assertEquals(menuResponse.getId(), response.getId());
        assertEquals(menuResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }
}