package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.UserRoleHelper;
import com.grasia.prima.ppi.api.repository.UserRoleRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class UserRoleServiceImpl extends AbstractCrudService implements UserRoleService {

    private final UserRoleRepository userRoleRepository;

    @Override
    public List<UserRoleResponse> findAll(SearchDto searchDto) {
        List<MUserRole> userRoles = userRoleRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return userRoles.stream().map(UserRoleHelper::toUserRoleResponse).toList();
    }

    @Override
    public Page<UserRoleResponse> findAllPagination(PageDto pageDto) {
        Page<MUserRole> userRoles = userRoleRepository.findAll(getSpecificationFindAll(pageDto), pageableSortByIdAsc(pageDto));
        return userRoles.map(UserRoleHelper::toUserRoleResponse);
    }

    @Override
    public UserRoleResponse findById(Long id) {
        MUserRole userRole = getUserRoleById(id);
        return UserRoleHelper.toUserRoleResponse(userRole);
    }

    @Override
    public UserRoleResponse create(UserRoleRequest request, HeaderRequest header) {
        MUserRole userRole = MUserRole.builder().build();
        setUserRole(userRole, request);
        setCreatedBy(userRole, header);
        setUpdatedBy(userRole, header);

        userRole = userRoleRepository.save(userRole);
        return UserRoleHelper.toUserRoleResponse(userRole);
    }

    @Override
    public UserRoleResponse update(Long id, UserRoleRequest request, HeaderRequest header) {
        MUserRole userRole = getUserRoleById(id);
        setUserRole(userRole, request);
        setUpdatedBy(userRole, header);

        userRole = userRoleRepository.save(userRole);
        return UserRoleHelper.toUserRoleResponse(userRole);
    }

    @Override
    public UserRoleResponse delete(Long id) {
        MUserRole userRole = getUserRoleById(id);
        userRole.setDeleted(true);

        userRole = userRoleRepository.save(userRole);
        return UserRoleHelper.toUserRoleResponse(userRole);
    }

    private Specification<MUserRole> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MUserRole> spec = SpecificationHelper.stringLike(MUserRole.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeletedFalse());
    }

    private MUserRole getUserRoleById(Long id) {
        return userRoleRepository.findByIdAndIsDeleted(id, false)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    private void setUserRole(MUserRole userRole, UserRoleRequest request) {
        userRole.setName(request.getName());
    }
}
