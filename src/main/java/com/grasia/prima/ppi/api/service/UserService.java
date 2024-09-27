package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserCreateRequest;
import com.grasia.prima.ppi.api.dto.request.UserUpdateRequest;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserResponse> findAllPagination(SearchDto searchDto);

    UserResponse findById(Long id);

    UserResponse create(UserCreateRequest request, HeaderRequest header);

    UserResponse update(Long id, UserUpdateRequest request, HeaderRequest header);

    UserResponse delete(Long id, HeaderRequest header);
}
