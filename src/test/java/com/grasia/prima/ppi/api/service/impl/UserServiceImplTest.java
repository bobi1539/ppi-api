package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.UserCreateRequest;
import com.grasia.prima.ppi.api.dto.request.UserUpdateRequest;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
import com.grasia.prima.ppi.api.service.UserRoleService;
import com.grasia.prima.ppi.api.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends ServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService roleService;

    @Mock
    private SystemParameterListService parameterListService;

    @Mock
    private UserValidationService userValidationService;

    private final MUser user = ObjectDummy.getUser();
    private final UserCreateRequest createRequest = ObjectDummy.getUserCreateRequest();
    private final UserUpdateRequest updateRequest = ObjectDummy.getUserUpdateRequest();
    private final MUserRole userRole = ObjectDummy.getUserRole();
    private final MSystemParameterList parameterList = ObjectDummy.getSystemParameterList();
    private static final String USERNAME = "admin";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadByUsername_Success() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(USERNAME);
        assertEquals(user.getUsername(), userDetails.getUsername());

        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    void testLoadByUsername_NotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> userService.loadUserByUsername(USERNAME));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(userRepository).findByUsername(USERNAME);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getUserPage());

        Page<UserResponse> responses = userService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(userRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MUser> getUserPage() {
        return new PageImpl<>(List.of(user, user));
    }

    @Test
    void testFindById_Success() {
        when(userRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(user));

        UserResponse response = userService.findById(id);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());

        verify(userRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindById_NotFound() {
        when(userRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> userService.findById(id));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(userRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        userValidationService.validateCreateUsername(anyString());
        when(roleService.getUserRoleById(id)).thenReturn(userRole);
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.create(createRequest, header);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());

        verify(roleService).getUserRoleById(id);
        verify(parameterListService).getSystemParameterListById(id);
        verify(userRepository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(roleService.getUserRoleById(id)).thenReturn(userRole);
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(userRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.update(id, updateRequest, header);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());

        verify(roleService).getUserRoleById(id);
        verify(parameterListService).getSystemParameterListById(id);
        verify(userRepository).findByIdAndIsDeleted(id, false);
        verify(userRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.delete(id, header);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());
        assertTrue(response.isDeleted());

        verify(userRepository).findById(id);
        verify(userRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        user.setDeleted(true);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponse response = userService.delete(id, header);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());
        assertTrue(response.isDeleted());

        verify(userRepository).findById(id);
        verify(userRepository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(userRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserResponse response = userService.restore(id, header);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFullName(), response.getFullName());
        assertFalse(response.isDeleted());

        verify(userRepository).findByIdAndIsDeleted(id, true);
        verify(userRepository).save(any());
    }
}