package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterResponse;
import com.grasia.prima.ppi.api.service.SystemParameterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.SYSTEM_PARAMETER)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class SystemParameterController extends BaseController {

    private final SystemParameterService systemParameterService;

    @GetMapping("/all")
    public List<SystemParameterResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return systemParameterService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<SystemParameterResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return systemParameterService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public SystemParameterResponse findById(@PathVariable Long id) {
        return systemParameterService.findById(id);
    }

    @PostMapping
    public SystemParameterResponse create(
            @RequestBody @Valid SystemParameterRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return systemParameterService.create(request, header);
    }

    @PutMapping("/{id}")
    public SystemParameterResponse update(
            @PathVariable Long id,
            @RequestBody @Valid SystemParameterRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return systemParameterService.update(id, request, header);
    }

}
