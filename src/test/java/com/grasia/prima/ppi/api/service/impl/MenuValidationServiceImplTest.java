package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MenuValidationServiceImplTest {

    @InjectMocks
    private MenuValidationServiceImpl service;

    @Mock
    private MenuRepository repository;

    private final MMenu menu = ObjectDummy.getMenu();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateCreateSequence_Success() {
        when(repository.findBySequence(1)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateCreateSequence(1));
    }

    @Test
    void testValidateCreateSequence_LessThanZero() {
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateSequence(-10));
        assertEquals(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO.status, e.getStatus());
        assertEquals(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO.message, e.getMessage());
    }

    @Test
    void testValidateCreateSequence_HasBeenRegistered() {
        when(repository.findBySequence(1)).thenReturn(Optional.of(menu));
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateSequence(1));
        assertEquals(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidateUpdateSequence_Success() {
        when(repository.findBySequence(1)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateUpdateSequence(menu, 1));
    }

    @Test
    void testValidateUpdateSequence_SequencePresentAndEqualsToExisting() {
        when(repository.findBySequence(1)).thenReturn(Optional.of(menu));
        assertDoesNotThrow(() -> service.validateUpdateSequence(menu, 1));
    }

    @Test
    void testValidateUpdateSequence_SequencePresentAndNotEqualsToExisting() {
        when(repository.findBySequence(1)).thenReturn(Optional.of(menu));

        MMenu existing = ObjectDummy.getMenu();
        existing.setId(2L);
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateUpdateSequence(existing, 1));
        assertEquals(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.MENU_SEQUENCE_HAS_BEEN_REGISTERED.message, e.getMessage());
    }
}