package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleMenuRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.UserRoleMenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRoleMenuControllerTest {

    @InjectMocks
    private UserRoleMenuController controller;

    @Mock
    private UserRoleMenuService service;

    private final UserRoleMenuRequest userRoleMenuRequest = ObjectDummy.getUserRoleMenuRequest();
    private final UserRoleMenuResponse userRoleMenuResponse = ObjectDummy.getUserRoleMenuResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByHeader() {
        HeaderRequest header = ObjectDummy.getHeaderRequest();
        when(service.findByHeader(header)).thenReturn(userRoleMenuResponse);

        UserRoleMenuResponse response = controller.findByHeader(header);
        assertEquals(userRoleMenuResponse.getUserRoleId(), response.getUserRoleId());
        assertEquals(userRoleMenuResponse.getName(), response.getName());

        verify(service).findByHeader(header);
    }

    @Test
    void testFindByUserRoleId() {
        Long userRoleId = 1L;
        when(service.findByUserRoleId(userRoleId)).thenReturn(userRoleMenuResponse);

        UserRoleMenuResponse response = controller.findByUserRoleId(userRoleId);
        assertEquals(userRoleMenuResponse.getUserRoleId(), response.getUserRoleId());
        assertEquals(userRoleMenuResponse.getName(), response.getName());

        verify(service).findByUserRoleId(userRoleId);
    }

    @Test
    void testCreate() {
        when(service.create(any())).thenReturn(userRoleMenuResponse);

        UserRoleMenuResponse response = controller.create(userRoleMenuRequest);
        assertEquals(userRoleMenuResponse.getUserRoleId(), response.getUserRoleId());
        assertEquals(userRoleMenuResponse.getName(), response.getName());

        verify(service).create(any());
    }

}