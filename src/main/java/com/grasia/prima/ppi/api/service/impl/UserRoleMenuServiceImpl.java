package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleMenuRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleSubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.entity.*;
import com.grasia.prima.ppi.api.helper.entity.MenuHelper;
import com.grasia.prima.ppi.api.helper.entity.SubMenuHelper;
import com.grasia.prima.ppi.api.repository.UserRoleMenuRepository;
import com.grasia.prima.ppi.api.repository.UserRoleSubMenuRepository;
import com.grasia.prima.ppi.api.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class UserRoleMenuServiceImpl implements UserRoleMenuService {

    private final UserRoleMenuRepository roleMenuRepository;
    private final UserRoleSubMenuRepository roleSubMenuRepository;
    private final UserRoleService roleService;
    private final MenuService menuService;
    private final SubMenuService subMenuService;
    private final UserService userService;

    @Override
    public UserRoleMenuResponse findByHeader(HeaderRequest header) {
        MUser user = userService.getUserById(header.getUserId());
        return findByUserRoleId(user.getUserRole().getId());
    }

    @Override
    public UserRoleMenuResponse findByUserRoleId(Long userRoleId) {
        MUserRole userRole = getUserRoleById(userRoleId);

        List<TUserRoleMenu> userRoleMenus = roleMenuRepository.findByUserRoleOrderByMenuSequenceAsc(userRole);
        List<TUserRoleSubMenu> userRoleSubMenus = roleSubMenuRepository.findByUserRoleOrderBySubMenuSequenceAsc(userRole);
        List<MenuResponse> menuResponses = createMenuResponses(userRoleMenus, userRoleSubMenus);

        return toResponse(userRole, menuResponses);
    }

    @Transactional
    @Override
    public UserRoleMenuResponse create(UserRoleMenuRequest request) {
        MUserRole userRole = getUserRoleById(request.getUserRoleId());
        deleteByUserRole(userRole);

        List<TUserRoleMenu> userRoleMenus = new ArrayList<>();
        List<TUserRoleSubMenu> userRoleSubMenus = new ArrayList<>();

        for (UserRoleSubMenuRequest roleSubMenuRequest : request.getMenuIds()) {
            MMenu menu = getMenuById(roleSubMenuRequest.getMenuId());
            TUserRoleMenu userRoleMenu = saveUserRoleMenu(userRole, menu);
            userRoleMenus.add(userRoleMenu);

            List<MSubMenu> subMenus = subMenuService.getSubMenuByIdsAndMenu(roleSubMenuRequest.getSubMenuIds(), menu);
            for (MSubMenu subMenu : subMenus) {
                TUserRoleSubMenu userRoleSubMenu = saveUserRoleSubMenu(subMenu, userRole);
                userRoleSubMenus.add(userRoleSubMenu);
            }
        }

        List<MenuResponse> menuResponses = createMenuResponses(userRoleMenus, userRoleSubMenus);
        return toResponse(userRole, menuResponses);
    }

    private List<MenuResponse> createMenuResponses(List<TUserRoleMenu> userRoleMenus, List<TUserRoleSubMenu> userRoleSubMenus) {
        return userRoleMenus.stream()
                .map(userRoleMenu -> createMenuResponse(userRoleMenu, userRoleSubMenus))
                .toList();
    }

    private MenuResponse createMenuResponse(TUserRoleMenu userRoleMenu, List<TUserRoleSubMenu> userRoleSubMenus) {
        MMenu menu = userRoleMenu.getMenu();

        List<SubMenuResponse> subMenuResponses = getSubMenuResponsesForMenu(menu, userRoleSubMenus);

        MenuResponse menuResponse = MenuHelper.toMenuResponse(menu);
        menuResponse.setSubMenus(subMenuResponses);
        return menuResponse;
    }

    private List<SubMenuResponse> getSubMenuResponsesForMenu(MMenu menu, List<TUserRoleSubMenu> userRoleSubMenus) {
        return userRoleSubMenus.stream()
                .filter(roleSubMenu -> roleSubMenu.getSubMenu().getMenu().getId().equals(menu.getId()))
                .map(roleSubMenu -> SubMenuHelper.toSubMenuResponse(roleSubMenu.getSubMenu()))
                .toList();
    }

    private MUserRole getUserRoleById(Long userRoleId) {
        return roleService.getUserRoleById(userRoleId);
    }

    private void deleteByUserRole(MUserRole userRole) {
        roleMenuRepository.deleteByUserRole(userRole);
        roleSubMenuRepository.deleteByUserRole(userRole);
    }

    private MMenu getMenuById(Long menuId) {
        return menuService.getMenuById(menuId);
    }

    private TUserRoleMenu saveUserRoleMenu(MUserRole userRole, MMenu menu) {
        TUserRoleMenu userRoleMenu = TUserRoleMenu.builder()
                .userRole(userRole)
                .menu(menu)
                .build();
        return roleMenuRepository.save(userRoleMenu);
    }

    private TUserRoleSubMenu saveUserRoleSubMenu(MSubMenu subMenu, MUserRole userRole) {
        TUserRoleSubMenu userRoleSubMenu = TUserRoleSubMenu.builder()
                .userRole(userRole)
                .subMenu(subMenu)
                .build();
        return roleSubMenuRepository.save(userRoleSubMenu);
    }

    private UserRoleMenuResponse toResponse(MUserRole userRole, List<MenuResponse> menuResponses) {
        return UserRoleMenuResponse.builder()
                .userRoleId(userRole.getId())
                .name(userRole.getName())
                .menus(menuResponses)
                .build();
    }
}
