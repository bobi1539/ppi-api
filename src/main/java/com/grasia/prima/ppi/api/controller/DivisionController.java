package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.DivisionRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DivisionResponse;
import com.grasia.prima.ppi.api.dto.search.DivisionSearchDto;
import com.grasia.prima.ppi.api.service.DivisionService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.DIVISION)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class DivisionController {

    private final DivisionService divisionService;

    @GetMapping("/all")
    public List<DivisionResponse> findAll(
            @RequestParam(required = false) Long periodId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        DivisionSearchDto searchDto = DivisionSearchDto.builder()
                .periodId(periodId)
                .search(search)
                .isDeleted(isDeleted)
                .build();
        return divisionService.findAll(searchDto);
    }

    @GetMapping
    public Page<DivisionResponse> findAllPagination(
            @RequestParam(required = false) Long periodId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        DivisionSearchDto searchDto = DivisionSearchDto.builder()
                .periodId(periodId)
                .search(search)
                .isDeleted(isDeleted)
                .page(page)
                .size(size)
                .build();
        return divisionService.findAllPagination(searchDto);
    }

    @GetMapping("/{id}")
    public DivisionResponse findById(@PathVariable Long id) {
        return divisionService.findById(id);
    }

    @PostMapping
    public DivisionResponse create(
            @RequestBody @Valid DivisionRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return divisionService.create(request, header);
    }

    @PutMapping("/{id}")
    public DivisionResponse update(
            @PathVariable Long id,
            @RequestBody @Valid DivisionRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return divisionService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public DivisionResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return divisionService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public DivisionResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return divisionService.restore(id, header);
    }
}
