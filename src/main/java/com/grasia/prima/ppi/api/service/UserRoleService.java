package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;

public interface UserRoleService extends CrudService<UserRoleRequest, UserRoleResponse, SearchDto> {
}
