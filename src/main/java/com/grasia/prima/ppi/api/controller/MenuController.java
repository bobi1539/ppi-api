package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.MenuRequest;
import com.grasia.prima.ppi.api.dto.response.MenuResponse;
import com.grasia.prima.ppi.api.service.MenuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.MENU)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class MenuController extends BaseController {

    private final MenuService menuService;

    @GetMapping("/all")
    public List<MenuResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return menuService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<MenuResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return menuService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public MenuResponse findById(@PathVariable Long id) {
        return menuService.findById(id);
    }

    @PostMapping
    public MenuResponse create(
            @RequestBody @Valid MenuRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return menuService.create(request, header);
    }

    @PutMapping("/{id}")
    public MenuResponse update(
            @PathVariable Long id,
            @RequestBody @Valid MenuRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return menuService.update(id, request, header);
    }
}
