package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserRoleService {

    List<UserRoleResponse> findAll(SearchDto searchDto);

    Page<UserRoleResponse> findAllPagination(PageDto pageDto);

    UserRoleResponse findById(Long id);

    UserRoleResponse create(UserRoleRequest request, HeaderRequest header);

    UserRoleResponse update(Long id, UserRoleRequest request, HeaderRequest header);

    UserRoleResponse delete(Long id, HeaderRequest header);
}
