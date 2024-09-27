package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserCreateRequest;
import com.grasia.prima.ppi.api.dto.request.UserRequest;
import com.grasia.prima.ppi.api.dto.request.UserUpdateRequest;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.entity.MUserRole;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.UserHelper;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl extends AbstractCrudService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserRoleService roleService;
    private final SystemParameterListService parameterListService;
    private final UserValidationService userValidationService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    @Override
    public Page<UserResponse> findAllPagination(SearchDto searchDto) {
        Page<MUser> users = userRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return users.map(this::toResponse);
    }

    @Override
    public UserResponse findById(Long id) {
        MUser user = getUserById(id);
        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse create(UserCreateRequest request, HeaderRequest header) {
        userValidationService.validateCreateUsername(request.getUsername());
        userValidationService.validateCreateEmail(request.getEmail());
        userValidationService.validatePassword(request.getPassword(), request.getPasswordConfirm());

        MUser user = MUser.builder().build();
        setUser(user, request);
        setUserPassword(user, request.getPassword());
        setCreatedBy(user, header);
        setUpdatedBy(user, header);

        user = userRepository.save(user);
        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse update(Long id, UserUpdateRequest request, HeaderRequest header) {
        MUser user = getUserById(id);
        userValidationService.validateUpdateUsername(user, request.getUsername());
        userValidationService.validateUpdateEmail(user, request.getEmail());

        setUser(user, request);
        setUpdatedBy(user, header);

        user = userRepository.save(user);
        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse delete(Long id, HeaderRequest header) {
        MUser user = userRepository.findById(id).orElseThrow(getNotFoundException());
        if (user.isDeleted()) {
            userRepository.delete(user);
        } else {
            user.setDeleted(true);
            setUpdatedBy(user, header);
            user = userRepository.save(user);
        }
        return toResponse(user);
    }

    @Transactional
    @Override
    public UserResponse restore(Long id, HeaderRequest header) {
        MUser user = getUserDeleted(id);
        user.setDeleted(false);
        setUpdatedBy(user, header);

        user = userRepository.save(user);
        return toResponse(user);
    }

    @Override
    public MUser getUserById(Long id) {
        return userRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MUser> getSpecificationFindAll(SearchDto searchDto) {
        return getSpecificationStringLike(searchDto.getSearch()).and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private Specification<MUser> getSpecificationStringLike(String value) {
        Specification<MUser> spec = SpecificationHelper.stringLike(MUser.FIELD_USERNAME, value);
        return spec
                .or(SpecificationHelper.stringLike(MUser.FIELD_FULL_NAME, value))
                .or(SpecificationHelper.stringLike(MUser.FIELD_EMAIL, value));
    }

    private UserResponse toResponse(MUser user) {
        return UserHelper.toUserResponse(user);
    }

    private void setUser(MUser user, UserRequest request) {
        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setEducation(request.getEducation());
        user.setGraduation(request.getGraduation());
        user.setUserRole(getUserRoleById(request.getUserRoleId()));
        user.setGender(getParameterListById(request.getGenderId()));
    }

    private void setUserPassword(MUser user, String password) {
        user.setPassword(passwordEncoder.encode(password));
    }

    private MUserRole getUserRoleById(Long id) {
        return roleService.getUserRoleById(id);
    }

    private MUser getUserDeleted(Long id) {
        return userRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private MSystemParameterList getParameterListById(Long id) {
        return parameterListService.getSystemParameterListById(id);
    }
}
