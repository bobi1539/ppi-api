package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.entity.MUser;

public interface UserValidationService {

    void validateCreateUsername(String username);

    void validateCreateEmail(String email);

    void validateUpdateUsername(MUser userExisting, String usernameRequest);

    void validateUpdateEmail(MUser userExisting, String emailRequest);

    void validatePassword(String password, String passwordConfirm);
}
