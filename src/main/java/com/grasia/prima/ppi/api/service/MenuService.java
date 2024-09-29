package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.MenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MMenu;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {

    List<MenuResponse> findAll(SearchDto searchDto);

    Page<MenuResponse> findAllPagination(SearchDto searchDto);

    MenuResponse findById(Long id);

    MenuResponse create(MenuRequest request, HeaderRequest header);

    MenuResponse update(Long id, MenuRequest request, HeaderRequest header);

    MMenu getMenuById(Long id);
}
