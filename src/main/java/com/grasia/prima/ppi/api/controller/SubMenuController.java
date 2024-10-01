package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.SubMenuRequest;
import com.grasia.prima.ppi.api.dto.response.SubMenuResponse;
import com.grasia.prima.ppi.api.service.SubMenuService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.SUB_MENU)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class SubMenuController {

    private final SubMenuService subMenuService;

    @GetMapping("/{id}")
    public SubMenuResponse findById(@PathVariable Long id) {
        return subMenuService.findById(id);
    }

    @PostMapping
    public SubMenuResponse create(
            @RequestBody @Valid SubMenuRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return subMenuService.create(request, header);
    }

    @PutMapping("/{id}")
    public SubMenuResponse update(
            @PathVariable Long id,
            @RequestBody @Valid SubMenuRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return subMenuService.update(id, request, header);
    }
}
