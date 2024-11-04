package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StudentRequest;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.service.StudentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.STUDENT)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class StudentController extends BaseController {

    private final StudentService studentService;

    @GetMapping("/all")
    public List<StudentResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return studentService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<StudentResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return studentService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    public StudentResponse create(
            @RequestBody @Valid StudentRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return studentService.create(request, header);
    }

    @PutMapping("/{id}")
    public StudentResponse update(
            @PathVariable Long id,
            @RequestBody @Valid StudentRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return studentService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public StudentResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return studentService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public StudentResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return studentService.restore(id, header);
    }
}
