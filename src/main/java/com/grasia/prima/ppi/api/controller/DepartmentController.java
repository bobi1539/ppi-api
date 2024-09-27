package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.DepartmentRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.service.DepartmentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.DEPARTMENT)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @GetMapping("/all")
    public List<DepartmentResponse> findAll(@RequestParam(required = false) String search) {
        return departmentService.findAll(buildSearchDto(search, 0, 0));
    }

    @GetMapping
    public Page<DepartmentResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return departmentService.findAllPagination(buildSearchDto(search, page, size));
    }

    @GetMapping("/{id}")
    public DepartmentResponse findById(@PathVariable Long id) {
        return departmentService.findById(id);
    }

    @PostMapping
    public DepartmentResponse create(
            @RequestBody @Valid DepartmentRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return departmentService.create(request, header);
    }

    @PutMapping("/{id}")
    public DepartmentResponse update(
            @PathVariable Long id,
            @RequestBody @Valid DepartmentRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return departmentService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public DepartmentResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return departmentService.delete(id, header);
    }
}
