package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.SubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SubMenuRepository;
import com.grasia.prima.ppi.api.service.MenuService;
import com.grasia.prima.ppi.api.service.SubMenuValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubMenuServiceImplTest extends ServiceTest {

    @InjectMocks
    private SubMenuServiceImpl subMenuService;

    @Mock
    private SubMenuRepository subMenuRepository;

    @Mock
    private MenuService menuService;

    @Mock
    private SubMenuValidationService subMenuValidationService;

    private final MSubMenu subMenu = ObjectDummy.getSubMenu();
    private final SubMenuRequest request = ObjectDummy.getSubMenuRequest();
    private final MMenu menu = ObjectDummy.getMenu();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        when(subMenuRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(subMenu));

        SubMenuResponse response = subMenuService.findById(id);
        assertEquals(subMenu.getId(), response.getId());
        assertEquals(subMenu.getName(), response.getName());

        verify(subMenuRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(menuService.getMenuById(id)).thenReturn(menu);
        when(subMenuRepository.save(any())).thenReturn(subMenu);

        SubMenuResponse response = subMenuService.create(request, header);
        assertEquals(subMenu.getId(), response.getId());
        assertEquals(subMenu.getName(), response.getName());

        verify(menuService).getMenuById(id);
        verify(subMenuValidationService).validateCreateSequence(1, menu);
        verify(subMenuRepository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(menuService.getMenuById(id)).thenReturn(menu);
        when(subMenuRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(subMenu));
        when(subMenuRepository.save(any())).thenReturn(subMenu);

        SubMenuResponse response = subMenuService.update(id, request, header);
        assertEquals(subMenu.getId(), response.getId());
        assertEquals(subMenu.getName(), response.getName());

        verify(menuService).getMenuById(id);
        verify(subMenuValidationService).validateUpdateSequence(subMenu, 1, menu);
        verify(subMenuRepository).findByIdAndIsDeleted(id, false);
        verify(subMenuRepository).save(any());
    }

    @Test
    void testGetSubMenuByIdsAndMenu_Success() {
        List<Long> ids = List.of(1L);
        when(subMenuRepository.findByIdInAndMenuAndIsDeleted(ids, menu, false)).thenReturn(List.of(subMenu));

        List<MSubMenu> subMenus = subMenuService.getSubMenuByIdsAndMenu(ids, menu);
        assertEquals(1, subMenus.size());

        verify(subMenuRepository).findByIdInAndMenuAndIsDeleted(ids, menu, false);
    }
}