package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleMenuRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.entity.*;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.UserRoleMenuRepository;
import com.grasia.prima.ppi.api.repository.UserRoleSubMenuRepository;
import com.grasia.prima.ppi.api.service.MenuService;
import com.grasia.prima.ppi.api.service.SubMenuService;
import com.grasia.prima.ppi.api.service.UserRoleService;
import com.grasia.prima.ppi.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRoleMenuServiceImplTest {

    @InjectMocks
    private UserRoleMenuServiceImpl userRoleMenuService;

    @Mock
    private UserRoleMenuRepository roleMenuRepository;

    @Mock
    private UserRoleSubMenuRepository roleSubMenuRepository;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private MenuService menuService;

    @Mock
    private SubMenuService subMenuService;

    @Mock
    private UserService userService;

    private final UserRoleMenuRequest userRoleMenuRequest = ObjectDummy.getUserRoleMenuRequest();
    private final TUserRoleMenu userRoleMenu = ObjectDummy.getUserRoleMenu();
    private final TUserRoleSubMenu userRoleSubMenu = ObjectDummy.getUserRoleSubMenu();
    private final MUserRole userRole = ObjectDummy.getUserRole();
    private final Long userRoleId = 1L;
    private final HeaderRequest header = ObjectDummy.getHeaderRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByHeader_Success() {
        when(userService.getUserById(1L)).thenReturn(ObjectDummy.getUser());
        when(userRoleService.getUserRoleById(userRoleId)).thenReturn(userRole);
        when(roleMenuRepository.findByUserRoleOrderByMenuSequenceAsc(userRole)).thenReturn(List.of(userRoleMenu));
        when(roleSubMenuRepository.findByUserRoleOrderBySubMenuSequenceAsc(userRole)).thenReturn(List.of(userRoleSubMenu));

        UserRoleMenuResponse response = userRoleMenuService.findByHeader(header);
        assertEquals(userRole.getId(), response.getUserRoleId());
        assertEquals(userRole.getName(), response.getName());
        assertEquals(1, response.getMenus().size());

        verify(userService).getUserById(1L);
        verify(userRoleService).getUserRoleById(userRoleId);
        verify(roleMenuRepository).findByUserRoleOrderByMenuSequenceAsc(userRole);
        verify(roleSubMenuRepository).findByUserRoleOrderBySubMenuSequenceAsc(userRole);
    }

    @Test
    void testFindByUserRoleId_Success() {
        when(userRoleService.getUserRoleById(userRoleId)).thenReturn(userRole);
        when(roleMenuRepository.findByUserRoleOrderByMenuSequenceAsc(userRole)).thenReturn(List.of(userRoleMenu));
        when(roleSubMenuRepository.findByUserRoleOrderBySubMenuSequenceAsc(userRole)).thenReturn(List.of(userRoleSubMenu));

        UserRoleMenuResponse response = userRoleMenuService.findByUserRoleId(userRoleId);
        assertEquals(userRole.getId(), response.getUserRoleId());
        assertEquals(userRole.getName(), response.getName());
        assertEquals(1, response.getMenus().size());

        verify(userRoleService).getUserRoleById(userRoleId);
        verify(roleMenuRepository).findByUserRoleOrderByMenuSequenceAsc(userRole);
        verify(roleSubMenuRepository).findByUserRoleOrderBySubMenuSequenceAsc(userRole);
    }

    @Test
    void testCreate_Success() {
        MMenu menu = userRoleMenu.getMenu();
        MSubMenu subMenu = userRoleSubMenu.getSubMenu();

        when(userRoleService.getUserRoleById(userRoleId)).thenReturn(userRole);
        when(menuService.getMenuById(1L)).thenReturn(menu);
        when(roleMenuRepository.save(any())).thenReturn(userRoleMenu);
        when(subMenuService.getSubMenuByIdsAndMenu(any(), any())).thenReturn(List.of(subMenu));
        when(roleSubMenuRepository.save(any())).thenReturn(userRoleSubMenu);

        UserRoleMenuResponse response = userRoleMenuService.create(userRoleMenuRequest);
        assertEquals(userRole.getId(), response.getUserRoleId());
        assertEquals(userRole.getName(), response.getName());
        assertEquals(1, response.getMenus().size());

        verify(userRoleService).getUserRoleById(userRoleId);
        verify(menuService).getMenuById(1L);
        verify(roleMenuRepository).save(any());
        verify(subMenuService).getSubMenuByIdsAndMenu(any(), any());
        verify(roleSubMenuRepository).save(any());
    }
}