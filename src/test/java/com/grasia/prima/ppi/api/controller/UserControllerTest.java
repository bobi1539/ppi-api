package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.ChangePasswordRequest;
import com.grasia.prima.ppi.api.dto.request.UserCreateRequest;
import com.grasia.prima.ppi.api.dto.request.UserUpdateRequest;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.UserService;
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

class UserControllerTest extends ControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;
    private final UserCreateRequest createRequest = ObjectDummy.getUserCreateRequest();
    private final UserUpdateRequest updateRequest = ObjectDummy.getUserUpdateRequest();
    private final ChangePasswordRequest changePasswordRequest = ObjectDummy.getChangePasswordRequest();
    private final UserResponse userResponse = ObjectDummy.getUserResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getUserResponsePage());

        Page<UserResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service, times(1)).findAllPagination(any());
    }

    private Page<UserResponse> getUserResponsePage() {
        return new PageImpl<>(List.of(userResponse, userResponse));
    }

    @Test
    void testFindByHeader() {
        when(service.findByHeader(header)).thenReturn(userResponse);

        UserResponse response = controller.findByHeader(header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).findByHeader(header);
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(userResponse);

        UserResponse response = controller.findById(id);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(userResponse);

        UserResponse response = controller.create(createRequest, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(userResponse);

        UserResponse response = controller.update(id, updateRequest, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(userResponse);

        UserResponse response = controller.delete(id, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(userResponse);

        UserResponse response = controller.restore(id, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service, times(1)).restore(any(), any());
    }

    @Test
    void testChangePasswordById() {
        when(service.changePassword(any(), any(), any())).thenReturn(userResponse);

        UserResponse response = controller.changePasswordById(id, changePasswordRequest, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service).changePassword(any(), any(), any());
    }

    @Test
    void testChangePasswordByHeader() {
        when(service.changePassword(any(), any(), any())).thenReturn(userResponse);

        UserResponse response = controller.changePasswordByHeader(changePasswordRequest, header);
        assertEquals(userResponse.getId(), response.getId());
        assertEquals(userResponse.getName(), response.getName());

        verify(service).changePassword(any(), any(), any());
    }
}