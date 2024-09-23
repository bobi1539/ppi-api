package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.UserRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRoleControllerTest extends ControllerTest {

    @InjectMocks
    private UserRoleController controller;

    @Mock
    private UserRoleService service;

    private MockHttpServletRequest request;
    private final UserRoleRequest userRoleRequest = ObjectDummy.getUserRoleRequest();
    private final UserRoleResponse positionResponse = ObjectDummy.getUserRoleResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getUserRoleResponses());

        List<UserRoleResponse> responses = controller.findAll("");
        assertEquals(2, responses.size());

        verify(service, times(1)).findAll(any());
    }

    private List<UserRoleResponse> getUserRoleResponses() {
        return List.of(positionResponse, positionResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getUserRoleResponsePage());

        Page<UserRoleResponse> responses = controller.findAllPagination("", 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service, times(1)).findAllPagination(any());
    }

    private Page<UserRoleResponse> getUserRoleResponsePage() {
        return new PageImpl<>(getUserRoleResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(positionResponse);

        UserRoleResponse response = controller.findById(id);
        assertEquals(positionResponse.getId(), response.getId());
        assertEquals(positionResponse.getName(), response.getName());

        verify(service, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(positionResponse);

        UserRoleResponse response = controller.create(userRoleRequest, header);
        assertEquals(positionResponse.getId(), response.getId());
        assertEquals(positionResponse.getName(), response.getName());

        verify(service, times(1)).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(positionResponse);

        UserRoleResponse response = controller.update(id, userRoleRequest, header);
        assertEquals(positionResponse.getId(), response.getId());
        assertEquals(positionResponse.getName(), response.getName());

        verify(service, times(1)).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any())).thenReturn(positionResponse);

        UserRoleResponse response = controller.delete(id);
        assertEquals(positionResponse.getId(), response.getId());
        assertEquals(positionResponse.getName(), response.getName());

        verify(service, times(1)).delete(any());
    }

    @Test
    void testBuildHeader() {
        request.setAttribute(Constant.HEADER, header);
        request.setQueryString("search=hello");
        HeaderRequest headerRequest = controller.buildHeader(request);
        assertEquals(header.getUserId(), headerRequest.getUserId());
        assertEquals(header.getUserFullName(), headerRequest.getUserFullName());
    }
}