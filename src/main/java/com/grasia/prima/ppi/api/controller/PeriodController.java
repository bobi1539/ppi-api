package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.PeriodRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.PeriodResponse;
import com.grasia.prima.ppi.api.service.PeriodService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.PERIOD)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class PeriodController extends BaseController {

    private final PeriodService periodService;

    @GetMapping("/all")
    public List<PeriodResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return periodService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<PeriodResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return periodService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public PeriodResponse findById(@PathVariable Long id) {
        return periodService.findById(id);
    }

    @PostMapping
    public PeriodResponse create(
            @RequestBody @Valid PeriodRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return periodService.create(request, header);
    }

    @PutMapping("/{id}")
    public PeriodResponse update(
            @PathVariable Long id,
            @RequestBody @Valid PeriodRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return periodService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public PeriodResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return periodService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public PeriodResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return periodService.restore(id, header);
    }
}
