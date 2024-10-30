package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.repository.SubMenuRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.MenuService;
import com.grasia.prima.ppi.api.service.SubMenuService;
import com.grasia.prima.ppi.api.service.SubMenuValidationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubMenuServiceImpl extends AbstractCrudService implements SubMenuService {

    private final SubMenuRepository subMenuRepository;
    private final MenuService menuService;
    private final SubMenuValidationService subMenuValidationService;

    @Override
    public SubMenuResponse findById(Long id) {
        return toResponse(getSubMenuById(id));
    }

    @Transactional
    @Override
    public SubMenuResponse create(SubMenuRequest request, HeaderRequest header) {
        MMenu menu = getMenuById(request.getMenuId());
        subMenuValidationService.validateCreateSequence(request.getSequence(), menu);

        MSubMenu subMenu = MSubMenu.builder().build();
        setSubMenu(subMenu, request, menu);
        setCreatedBy(subMenu, header);
        setUpdatedBy(subMenu, header);

        return toResponse(subMenuRepository.save(subMenu));
    }

    @Transactional
    @Override
    public SubMenuResponse update(Long id, SubMenuRequest request, HeaderRequest header) {
        MMenu menu = getMenuById(request.getMenuId());
        MSubMenu subMenu = getSubMenuById(id);
        subMenuValidationService.validateUpdateSequence(subMenu, request.getSequence(), menu);

        setSubMenu(subMenu, request, menu);
        setUpdatedBy(subMenu, header);

        return toResponse(subMenuRepository.save(subMenu));
    }

    @Override
    public MSubMenu getSubMenuById(Long id) {
        return subMenuRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    @Override
    public List<MSubMenu> getSubMenuByIdsAndMenu(List<Long> ids, MMenu menu) {
        return subMenuRepository.findByIdInAndMenuAndIsDeleted(ids, menu, false);
    }

    private void setSubMenu(MSubMenu subMenu, SubMenuRequest request, MMenu menu) {
        subMenu.setName(request.getName());
        subMenu.setRoute(request.getRoute());
        subMenu.setSequence(request.getSequence());
        subMenu.setMenu(menu);
    }

    private MMenu getMenuById(Long id) {
        return menuService.getMenuById(id);
    }

    private SubMenuResponse toResponse(MSubMenu subMenu) {
        return SubMenuResponse.toResponse(subMenu);
    }
}
