package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.MenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.MenuRepository;
import com.grasia.prima.ppi.api.service.MenuValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuServiceImplTest extends ServiceTest {

    @InjectMocks
    private MenuServiceImpl service;

    @Mock
    private MenuValidationService validationService;

    @Mock
    private MenuRepository repository;

    private final MMenu menu = ObjectDummy.getMenu();
    private final MenuRequest request = ObjectDummy.getMenuRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(repository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getMenus());

        List<MenuResponse> responses = service.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(repository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MMenu> getMenus() {
        return List.of(menu, menu);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getMenuPage());

        Page<MenuResponse> responses = service.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MMenu> getMenuPage() {
        return new PageImpl<>(getMenus());
    }

    @Test
    void testFindById_Success() {
        menu.setSubMenus(List.of(ObjectDummy.getSubMenu()));
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(menu));

        MenuResponse response = service.findById(id);
        assertEquals(menu.getId(), response.getId());
        assertEquals(menu.getName(), response.getName());
        assertEquals(menu.getSubMenus().size(), response.getSubMenus().size());

        verify(repository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(repository.save(any())).thenReturn(menu);

        MenuResponse response = service.create(request, header);
        assertEquals(menu.getId(), response.getId());
        assertEquals(menu.getName(), response.getName());

        verify(validationService).validateCreateSequence(1);
        verify(repository).save(any());
    }

    @Test
    void testUpdate_Success() {
        when(repository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(menu));
        when(repository.save(any())).thenReturn(menu);

        MenuResponse response = service.update(id, request, header);
        assertEquals(menu.getId(), response.getId());
        assertEquals(menu.getName(), response.getName());

        verify(validationService).validateUpdateSequence(menu, 1);
        verify(repository).findByIdAndIsDeleted(id, false);
        verify(repository).save(any());
    }
}