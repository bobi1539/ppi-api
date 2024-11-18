package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;

public interface SecretKeyService {

    SecretKeyResponse findByName(String name);

    SecretKeyResponse generate(String name);

    void verify(String key);
}
