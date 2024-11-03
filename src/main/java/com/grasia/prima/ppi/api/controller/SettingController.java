package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SettingRequest;
import com.grasia.prima.ppi.api.dto.response.SettingResponse;
import com.grasia.prima.ppi.api.service.SettingService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.SETTING)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class SettingController extends BaseController {

    private final SettingService settingService;

    @GetMapping("/{id}")
    public SettingResponse findById(@PathVariable Long id) {
        return settingService.findById(id);
    }

    @PostMapping
    public SettingResponse create(
            @RequestBody @Valid SettingRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return settingService.create(request, header);
    }

    @PutMapping("/{id}")
    public SettingResponse update(
            @PathVariable Long id,
            @RequestBody @Valid SettingRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return settingService.update(id, request, header);
    }
}
