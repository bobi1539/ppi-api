package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SettingRequest;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.entity.MSetting;

public interface SettingService {

    SettingResponse findById(Long id);

    SettingResponse create(SettingRequest request, HeaderRequest header);

    SettingResponse update(Long id, SettingRequest request, HeaderRequest header);

    MSetting getById(Long id);
}
