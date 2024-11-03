package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.service.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.WEB_SETTING)
@AllArgsConstructor
public class WebSettingController extends BaseController {

    private final SettingService settingService;

    @GetMapping("/{id}")
    public SettingResponse findById(@PathVariable Long id) {
        return settingService.findById(id);
    }
}
