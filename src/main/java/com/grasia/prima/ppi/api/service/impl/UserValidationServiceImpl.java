package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.repository.UserRepository;
import com.grasia.prima.ppi.api.service.UserValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserValidationServiceImpl implements UserValidationService {

    private final UserRepository userRepository;

    @Override
    public void validateCreateUsername(String username) {
        Optional<MUser> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new BusinessException(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validateCreateEmail(String email) {
        Optional<MUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new BusinessException(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validateUpdateUsername(MUser userExisting, String usernameRequest) {
        Optional<MUser> optional = userRepository.findByUsername(usernameRequest);
        if (optional.isPresent() && !optional.get().getId().equals(userExisting.getId())) {
            throw new BusinessException(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validateUpdateEmail(MUser userExisting, String emailRequest) {
        Optional<MUser> optional = userRepository.findByEmail(emailRequest);
        if (optional.isPresent() && !optional.get().getId().equals(userExisting.getId())) {
            throw new BusinessException(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED);
        }
    }

    @Override
    public void validatePassword(String password, String passwordConfirm) {
        if (password.length() < Constant.MIN_PASSWORD_LENGTH) {
            throw new BusinessException(GlobalMessage.MIN_PASSWORD_LENGTH);
        }

        if (!StringHelper.isContainUpperCaseLetter(password)) {
            throw new BusinessException(GlobalMessage.MUST_CONTAIN_UPPER_CASE);
        }

        if (!StringHelper.isContainNumber(password)) {
            throw new BusinessException(GlobalMessage.MUST_CONTAIN_NUMBER);
        }

        if (!password.equals(passwordConfirm)) {
            throw new BusinessException(GlobalMessage.PASSWORD_CONFIRM_NOT_EQUALS);
        }
    }
}
