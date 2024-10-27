package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StaffRequest;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.dto.search.StaffSearchDto;
import com.grasia.prima.ppi.api.service.StaffService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STAFF)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/all")
    public List<StaffResponse> findAll(
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) Boolean isHead,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        StaffSearchDto searchDto = StaffSearchDto.builder()
                .divisionId(divisionId)
                .isHead(isHead)
                .search(search)
                .isDeleted(isDeleted)
                .build();
        return staffService.findAll(searchDto);
    }

    @GetMapping
    public Page<StaffResponse> findAllPagination(
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) Boolean isHead,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        StaffSearchDto searchDto = StaffSearchDto.builder()
                .divisionId(divisionId)
                .isHead(isHead)
                .search(search)
                .isDeleted(isDeleted)
                .page(page)
                .size(size)
                .build();
        return staffService.findAllPagination(searchDto);
    }

    @GetMapping("/{id}")
    public StaffResponse findById(@PathVariable Long id) {
        return staffService.findById(id);
    }

    @PostMapping
    public StaffResponse create(
            @RequestBody @Valid StaffRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return staffService.create(request, header);
    }

    @PutMapping("/{id}")
    public StaffResponse update(
            @PathVariable Long id,
            @RequestBody @Valid StaffRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return staffService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public StaffResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return staffService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public StaffResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return staffService.restore(id, header);
    }
}
