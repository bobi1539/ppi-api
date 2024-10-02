package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.UserRoleMenuRequest;
import com.grasia.prima.ppi.api.dto.response.UserRoleMenuResponse;
import com.grasia.prima.ppi.api.service.UserRoleMenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.USER_ROLE_MENU)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class UserRoleMenuController {

    private final UserRoleMenuService userRoleMenuService;

    @GetMapping("/{userRoleId}")
    public UserRoleMenuResponse findByUserRoleId(@PathVariable Long userRoleId) {
        return userRoleMenuService.findByUserRoleId(userRoleId);
    }

    @PostMapping
    public UserRoleMenuResponse create(@RequestBody @Valid UserRoleMenuRequest request) {
        return userRoleMenuService.create(request);
    }
}
