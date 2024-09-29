package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.MenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.MenuHelper;
import com.grasia.prima.ppi.api.repository.MenuRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.MenuService;
import com.grasia.prima.ppi.api.service.MenuValidationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MenuServiceImpl extends AbstractCrudService implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuValidationService menuValidationService;

    @Override
    public List<MenuResponse> findAll(SearchDto searchDto) {
        List<MMenu> menus = menuRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return menus.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<MenuResponse> findAllPagination(SearchDto searchDto) {
        Page<MMenu> menus = menuRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return menus.map(this::toResponse);
    }

    @Override
    public MenuResponse findById(Long id) {
        return toResponse(getMenuById(id));
    }

    @Transactional
    @Override
    public MenuResponse create(MenuRequest request, HeaderRequest header) {
        menuValidationService.validateCreateSequence(request.getSequence());

        MMenu menu = MMenu.builder().build();
        setMenu(menu, request);
        setCreatedBy(menu, header);
        setUpdatedBy(menu, header);

        return toResponse(menuRepository.save(menu));
    }

    @Transactional
    @Override
    public MenuResponse update(Long id, MenuRequest request, HeaderRequest header) {
        MMenu menu = getMenuById(id);
        menuValidationService.validateUpdateSequence(menu, request.getSequence());

        setMenu(menu, request);
        setUpdatedBy(menu, header);

        return toResponse(menuRepository.save(menu));
    }

    @Override
    public MMenu getMenuById(Long id) {
        return menuRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MMenu> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MMenu> spec = SpecificationHelper.stringLike(MMenu.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setMenu(MMenu menu, MenuRequest request) {
        menu.setName(request.getName());
        menu.setRoute(request.getRoute());
        menu.setIcon(request.getIcon());
        menu.setSequence(request.getSequence());
    }

    private MenuResponse toResponse(MMenu menu) {
        return MenuHelper.toMenuResponse(menu);
    }
}
