package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.repository.UserRoleRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.UserRoleService;
import jakarta.transaction.Transactional;
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
        return userRoles.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<UserRoleResponse> findAllPagination(SearchDto searchDto) {
        Page<MUserRole> userRoles = userRoleRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return userRoles.map(this::toResponse);
    }

    @Override
    public UserRoleResponse findById(Long id) {
        MUserRole userRole = getUserRoleById(id);
        return toResponse(userRole);
    }

    @Transactional
    @Override
    public UserRoleResponse create(UserRoleRequest request, HeaderRequest header) {
        MUserRole userRole = MUserRole.builder().build();
        setUserRole(userRole, request);
        setCreatedBy(userRole, header);
        setUpdatedBy(userRole, header);

        userRole = userRoleRepository.save(userRole);
        return toResponse(userRole);
    }

    @Transactional
    @Override
    public UserRoleResponse update(Long id, UserRoleRequest request, HeaderRequest header) {
        MUserRole userRole = getUserRoleById(id);
        setUserRole(userRole, request);
        setUpdatedBy(userRole, header);

        userRole = userRoleRepository.save(userRole);
        return toResponse(userRole);
    }

    @Transactional
    @Override
    public UserRoleResponse delete(Long id, HeaderRequest header) {
        MUserRole userRole = userRoleRepository.findById(id).orElseThrow(getNotFoundException());
        if (userRole.isDeleted()) {
            userRoleRepository.delete(userRole);
        } else {
            userRole.setDeleted(true);
            setUpdatedBy(userRole, header);
            userRole = userRoleRepository.save(userRole);
        }
        return toResponse(userRole);
    }

    @Transactional
    @Override
    public UserRoleResponse restore(Long id, HeaderRequest header) {
        MUserRole userRole = getUserRoleDeleted(id);
        userRole.setDeleted(false);
        setUpdatedBy(userRole, header);

        userRole = userRoleRepository.save(userRole);
        return toResponse(userRole);
    }

    @Override
    public MUserRole getUserRoleById(Long id) {
        return userRoleRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MUserRole> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MUserRole> spec = SpecificationHelper.stringLike(MUserRole.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setUserRole(MUserRole userRole, UserRoleRequest request) {
        userRole.setName(request.getName());
    }

    private MUserRole getUserRoleDeleted(Long id) {
        return userRoleRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private UserRoleResponse toResponse(MUserRole userRole) {
        return UserRoleResponse.toResponse(userRole);
    }
}
