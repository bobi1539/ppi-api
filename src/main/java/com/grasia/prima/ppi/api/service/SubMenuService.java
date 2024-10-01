package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.entity.MSubMenu;

public interface SubMenuService {

    SubMenuResponse findById(Long id);

    SubMenuResponse create(SubMenuRequest request, HeaderRequest header);

    SubMenuResponse update(Long id, SubMenuRequest request, HeaderRequest header);

    MSubMenu getSubMenuById(Long id);
}
