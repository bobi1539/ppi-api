package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;
import com.grasia.prima.ppi.api.entity.TSecretKey;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.repository.SecretKeyRepository;
import com.grasia.prima.ppi.api.service.SecretKeyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class SecretKeyServiceImpl implements SecretKeyService {

    private final SecretKeyRepository secretKeyRepository;
    private static final int VALID_IN_DAYS = 2;

    @Override
    public SecretKeyResponse findByName(String name) {
        return toResponse(getByName(name));
    }

    @Override
    public SecretKeyResponse generate(String name) {
        TSecretKey secretKey = getByName(name);
        secretKey.setKey(StringHelper.random());
        secretKey.setValidDate(LocalDateTime.now().plusDays(VALID_IN_DAYS));
        return toResponse(secretKeyRepository.save(secretKey));
    }

    @Override
    public void verify(String key) {
        TSecretKey secretKey = getByKey(key);
        if (secretKey.getValidDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(GlobalMessage.KEY_NOT_VALID);
        }
    }

    private TSecretKey getByName(String name) {
        return secretKeyRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(GlobalMessage.DATA_NOT_FOUND));
    }

    private TSecretKey getByKey(String key) {
        return secretKeyRepository.findByKey(key)
                .orElseThrow(() -> new BusinessException(GlobalMessage.KEY_NOT_VALID));
    }

    private SecretKeyResponse toResponse(TSecretKey secretKey) {
        return SecretKeyResponse.toResponse(secretKey);
    }
}
