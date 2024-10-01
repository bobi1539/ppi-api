package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.SubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.SubMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubMenuControllerTest extends ControllerTest {

    @InjectMocks
    private SubMenuController controller;

    @Mock
    private SubMenuService service;

    private final SubMenuRequest subMenuRequest = ObjectDummy.getSubMenuRequest();
    private final SubMenuResponse subMenuResponse = ObjectDummy.getSubMenuResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(subMenuResponse);

        SubMenuResponse response = controller.findById(id);
        assertEquals(subMenuResponse.getId(), response.getId());
        assertEquals(subMenuResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(subMenuResponse);

        SubMenuResponse response = controller.create(subMenuRequest, header);
        assertEquals(subMenuResponse.getId(), response.getId());
        assertEquals(subMenuResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(subMenuResponse);

        SubMenuResponse response = controller.update(id, subMenuRequest, header);
        assertEquals(subMenuResponse.getId(), response.getId());
        assertEquals(subMenuResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }
}