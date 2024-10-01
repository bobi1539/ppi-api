package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SubMenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SubMenuValidationServiceImplTest {

    @InjectMocks
    private SubMenuValidationServiceImpl service;

    @Mock
    private SubMenuRepository repository;

    private final MSubMenu subMenu = ObjectDummy.getSubMenu();
    private final MMenu menu = ObjectDummy.getMenu();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateCreateSequence_Success() {
        when(repository.findBySequenceAndMenu(1, menu)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateCreateSequence(1, menu));
    }

    @Test
    void testValidateCreateSequence_LessThanZero() {
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateSequence(-10, menu));
        assertEquals(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO.status, e.getStatus());
        assertEquals(GlobalMessage.SEQUENCE_MUST_GREATER_THAN_ZERO.message, e.getMessage());
    }

    @Test
    void testValidateCreateSequence_HasBeenRegistered() {
        when(repository.findBySequenceAndMenu(1, menu)).thenReturn(Optional.of(subMenu));
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateCreateSequence(1, menu));
        assertEquals(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED.message, e.getMessage());
    }

    @Test
    void testValidateUpdateSequence_Success() {
        when(repository.findBySequenceAndMenu(1, menu)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> service.validateUpdateSequence(subMenu, 1, menu));
    }

    @Test
    void testValidateUpdateSequence_SequencePresentAndEqualsToExisting() {
        when(repository.findBySequenceAndMenu(1, menu)).thenReturn(Optional.of(subMenu));
        assertDoesNotThrow(() -> service.validateUpdateSequence(subMenu, 1, menu));
    }

    @Test
    void testValidateUpdateSequence_SequencePresentAndNotEqualsToExisting() {
        when(repository.findBySequenceAndMenu(1, menu)).thenReturn(Optional.of(subMenu));

        MSubMenu existing = ObjectDummy.getSubMenu();
        existing.setId(2L);
        BusinessException e = assertThrows(BusinessException.class, () -> service.validateUpdateSequence(existing, 1, menu));
        assertEquals(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED.status, e.getStatus());
        assertEquals(GlobalMessage.SUB_MENU_SEQUENCE_HAS_BEEN_REGISTERED.message, e.getMessage());
    }
}