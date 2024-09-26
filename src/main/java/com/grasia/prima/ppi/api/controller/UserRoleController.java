package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.UserRoleRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleResponse;
import com.grasia.prima.ppi.api.service.UserRoleService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.USER_ROLE)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class UserRoleController extends BaseController {

    private final UserRoleService userRoleService;

    @GetMapping("/all")
    public List<UserRoleResponse> findAll(@RequestParam(required = false) String search) {
        return userRoleService.findAll(buildSearchDto(search, 0, 0));
    }

    @GetMapping
    public Page<UserRoleResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return userRoleService.findAllPagination(buildSearchDto(search, page, size));
    }

    @GetMapping("/{id}")
    public UserRoleResponse findById(@PathVariable Long id) {
        return userRoleService.findById(id);
    }

    @PostMapping
    public UserRoleResponse create(
            @RequestBody @Valid UserRoleRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return userRoleService.create(request, header);
    }

    @PutMapping("/{id}")
    public UserRoleResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UserRoleRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return userRoleService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public UserRoleResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return userRoleService.delete(id, header);
    }
}
