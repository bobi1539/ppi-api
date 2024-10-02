package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.UserRoleMenuRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;

public interface UserRoleMenuService {

    UserRoleMenuResponse findByUserRoleId(Long userRoleId);

    UserRoleMenuResponse create(UserRoleMenuRequest request);
}
