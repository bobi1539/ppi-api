package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MUser;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserValidationServiceImplTest {

    @InjectMocks
    private UserValidationServiceImpl service;

    @Mock
    private UserRepository repository;

    private final MUser user = ObjectDummy.getUser();
    private final String username = "username";
    private final String email = "email";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateCreateUsername_Success() {
        when(repository.findByUsername(username)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateCreateUsername(username));
    }

    @Test
    void testValidateCreateUsername_HasBeenRegistered() {
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateUsername(username));
        assertEquals(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidateCreateEmail_Success() {
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateCreateEmail(email));
    }

    @Test
    void testValidateCreateEmail_HasBeenRegistered() {
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateEmail(email));
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidateUpdateUsername_Success() {
        when(repository.findByUsername(username)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateUpdateUsername(user, username));
    }

    @Test
    void testValidateUpdateUsername_UsernameRequestPresentAndEqualsToExisting() {
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> service.validateUpdateUsername(user, username));
    }

    @Test
    void testValidateUpdateUsername_UsernameRequestPresentAndNotEqualsToExisting() {
        when(repository.findByUsername(username)).thenReturn(Optional.of(user));

        MUser existing = ObjectDummy.getUser();
        existing.setId(2L);
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateUpdateUsername(existing, username));
        assertEquals(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.USERNAME_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidateUpdateEmail_Success() {
        when(repository.findByEmail(email)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateUpdateEmail(user, email));
    }

    @Test
    void testValidateUpdateUsername_EmailRequestPresentAndEqualsToExisting() {
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> service.validateUpdateEmail(user, email));
    }

    @Test
    void testValidateUpdateUsername_EmailRequestPresentAndNotEqualsToExisting() {
        when(repository.findByEmail(email)).thenReturn(Optional.of(user));

        MUser existing = ObjectDummy.getUser();
        existing.setId(2L);
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateUpdateEmail(existing, email));
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidatePassword_Success() {
        String password = "MyPassword88891";
        assertDoesNotThrow(() -> service.validatePassword(password, password));
    }

    @Test
    void testValidatePassword_MinLengthNotValid() {
        String password = "pass";
        BusinessException e = assertThrows(BusinessException.class, () -> service.validatePassword(password, password));
        assertEquals(GlobalMessage.MIN_PASSWORD_LENGTH.status, e.getStatus());
        assertEquals(GlobalMessage.MIN_PASSWORD_LENGTH.message, e.getMessage());
    }

    @Test
    void testValidatePassword_NotContainsUpperCase() {
        String password = "password_length";
        BusinessException e = assertThrows(BusinessException.class, () -> service.validatePassword(password, password));
        assertEquals(GlobalMessage.MUST_CONTAIN_UPPER_CASE.status, e.getStatus());
        assertEquals(GlobalMessage.MUST_CONTAIN_UPPER_CASE.message, e.getMessage());
    }

    @Test
    void testValidatePassword_NotContainNumber() {
        String password = "PasswordLength";
        BusinessException e = assertThrows(BusinessException.class, () -> service.validatePassword(password, password));
        assertEquals(GlobalMessage.MUST_CONTAIN_NUMBER.status, e.getStatus());
        assertEquals(GlobalMessage.MUST_CONTAIN_NUMBER.message, e.getMessage());
    }

    @Test
    void testValidatePassword_PasswordConfirmNotSame() {
        String password = "PasswordLength9981";
        String passwordConfirm = "notSame";
        BusinessException e = assertThrows(BusinessException.class, () -> service.validatePassword(password, passwordConfirm));
        assertEquals(GlobalMessage.PASSWORD_CONFIRM_NOT_EQUALS.status, e.getStatus());
        assertEquals(GlobalMessage.PASSWORD_CONFIRM_NOT_EQUALS.message, e.getMessage());
    }
}