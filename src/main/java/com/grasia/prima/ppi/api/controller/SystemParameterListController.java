package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SystemParameterListRequest;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.SYSTEM_PARAMETER_LIST)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class SystemParameterListController {

    private final SystemParameterListService parameterListService;

    @GetMapping("/all")
    public List<SystemParameterListResponse> findAll(
            @RequestParam Long systemParameterId,
            @RequestParam(required = false) String search
    ) {
        SystemParameterListSearchDto searchDto = SystemParameterListSearchDto.builder()
                .systemParameterId(systemParameterId)
                .search(search)
                .build();
        return parameterListService.findAll(searchDto);
    }

    @GetMapping
    public Page<SystemParameterListResponse> findAllPagination(
            @RequestParam Long systemParameterId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        SystemParameterListSearchDto searchDto = SystemParameterListSearchDto.builder()
                .systemParameterId(systemParameterId)
                .search(search)
                .page(page)
                .size(size)
                .build();
        return parameterListService.findAllPagination(searchDto);
    }

    @GetMapping("/{id}")
    public SystemParameterListResponse findById(@PathVariable Long id) {
        return parameterListService.findById(id);
    }

    @PostMapping
    public SystemParameterListResponse create(
            @RequestBody @Valid SystemParameterListRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return parameterListService.create(request, header);
    }

    @PutMapping("/{id}")
    public SystemParameterListResponse update(
            @PathVariable Long id,
            @RequestBody @Valid SystemParameterListRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return parameterListService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public SystemParameterListResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return parameterListService.delete(id, header);
    }
}
