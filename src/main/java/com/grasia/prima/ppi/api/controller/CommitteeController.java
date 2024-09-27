package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.CommitteeRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.CommitteeResponse;
import com.grasia.prima.ppi.api.service.CommitteeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.COMMITTEE)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class CommitteeController extends BaseController {

    private final CommitteeService committeeService;

    @GetMapping("/all")
    public List<CommitteeResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return committeeService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<CommitteeResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return committeeService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public CommitteeResponse findById(@PathVariable Long id) {
        return committeeService.findById(id);
    }

    @PostMapping
    public CommitteeResponse create(
            @RequestBody @Valid CommitteeRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return committeeService.create(request, header);
    }

    @PutMapping("/{id}")
    public CommitteeResponse update(
            @PathVariable Long id,
            @RequestBody @Valid CommitteeRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return committeeService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public CommitteeResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return committeeService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public CommitteeResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return committeeService.restore(id, header);
    }
}
