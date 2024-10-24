package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.*;
import com.grasia.prima.ppi.api.dto.response.UserResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.entity.MUserRole;
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

import java.util.Objects;

@AllArgsConstructor
@Service
public class UserServiceImpl extends AbstractCrudService implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserRoleService roleService;
    private final UserValidationService userValidationService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String DIRECTORY_NAME = "user";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(getNotFoundException());
    }

    @Override
    public Page<UserResponse> findAllPagination(SearchDto searchDto) {
        Page<MUser> users = userRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return users.map(this::toResponse);
    }

    @Override
    public UserResponse findByHeader(HeaderRequest header) {
        return toResponse(getUserById(header.getUserId()));
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
        savePhotoWhenCreate(user, request);
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
        savePhotoWhenUpdate(user, request);
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
            deleteFile(user.getPhoto());
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
                .or(SpecificationHelper.stringLike(MUser.FIELD_NAME, value))
                .or(SpecificationHelper.stringLike(MUser.FIELD_EMAIL, value));
    }

    private UserResponse toResponse(MUser user) {
        return UserHelper.toUserResponse(user);
    }

    private void setUser(MUser user, UserRequest request) {
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setIsActive(request.getIsActive());
        user.setDescription(request.getDescription());
        user.setUserRole(getUserRoleById(request.getUserRoleId()));
    }

    private boolean isPhotoRequestNotNull(UserRequest request) {
        return Objects.nonNull(request.getPhoto().getFileBase64()) && Objects.nonNull(request.getPhoto().getFileName());
    }

    private void savePhotoWhenCreate(MUser user, UserRequest request) {
        if (isPhotoRequestNotNull(request)) {
            user.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));
        }
    }

    private void savePhotoWhenUpdate(MUser user, UserRequest request) {
        if (isPhotoRequestNotNull(request) && !Objects.equals(user.getPhoto(), request.getPhoto().getFileName())) {
            deleteFile(user.getPhoto());
            user.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));
        }
    }

    private String saveFile(String fileName, String base64String) {
        Base64ToFileDto dto = Base64ToFileDto.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .base64String(base64String)
                .build();
        return fileService.saveFileFromBase64(dto);
    }

    private void deleteFile(String fileName) {
        if (Objects.nonNull(fileName)) {
            FileRequest fileRequest = FileRequest.builder()
                    .directoryName(DIRECTORY_NAME)
                    .fileName(fileName)
                    .build();
            fileService.deleteFile(fileRequest);
        }
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
}
