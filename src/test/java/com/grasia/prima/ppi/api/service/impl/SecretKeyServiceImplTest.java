package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;
import com.grasia.prima.ppi.api.entity.TSecretKey;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.SecretKeyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SecretKeyServiceImplTest {

    @InjectMocks
    private SecretKeyServiceImpl secretKeyService;

    @Mock
    private SecretKeyRepository secretKeyRepository;

    private final TSecretKey secretKey = ObjectDummy.getSecretKey();
    private final String secretKeyName = "secret-key-name";
    private final String key = "RjxCSsz4nFts54MvC8gv";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName() {
        when(secretKeyRepository.findByName(secretKeyName)).thenReturn(Optional.of(secretKey));

        SecretKeyResponse response = secretKeyService.findByName(secretKeyName);
        assertEquals(secretKey.getName(), response.getName());
        assertEquals(secretKey.getKey(), response.getKey());

        verify(secretKeyRepository).findByName(secretKeyName);
    }

    @Test
    void testGenerate_Success() {
        when(secretKeyRepository.findByName(secretKeyName)).thenReturn(Optional.of(secretKey));
        when(secretKeyRepository.save(any())).thenReturn(secretKey);

        SecretKeyResponse response = secretKeyService.generate(secretKeyName);
        assertEquals(secretKey.getName(), response.getName());
        assertEquals(secretKey.getKey(), response.getKey());

        verify(secretKeyRepository).findByName(secretKeyName);
        verify(secretKeyRepository).save(any());
    }

    @Test
    void testGenerate_NotFound() {
        when(secretKeyRepository.findByName(secretKeyName)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> secretKeyService.generate(secretKeyName));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(secretKeyRepository).findByName(secretKeyName);
    }

    @Test
    void testVerify_Success() {
        when(secretKeyRepository.findByKey(key)).thenReturn(Optional.of(secretKey));
        assertDoesNotThrow(() -> secretKeyService.verify(key));
        verify(secretKeyRepository).findByKey(key);
    }

    @Test
    void testVerify_NotFound() {
        when(secretKeyRepository.findByKey(key)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> secretKeyService.verify(key));
        assertEquals(GlobalMessage.KEY_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.KEY_NOT_VALID.message, e.getMessage());

        verify(secretKeyRepository).findByKey(key);
    }

    @Test
    void testVerify_KeyNotValid() {
        secretKey.setValidDate(LocalDateTime.now().minusDays(2));
        when(secretKeyRepository.findByKey(key)).thenReturn(Optional.of(secretKey));

        BusinessException e = assertThrows(BusinessException.class, () -> secretKeyService.verify(key));
        assertEquals(GlobalMessage.KEY_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.KEY_NOT_VALID.message, e.getMessage());

        verify(secretKeyRepository).findByKey(key);
    }
}