package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.WebStudentRequest;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.WEB_STUDENT)
@AllArgsConstructor
public class WebStudentController extends BaseController {

    private final StudentService studentService;

    @GetMapping("/count")
    public long countAll() {
        return studentService.countAll();
    }

    @PostMapping
    public StudentResponse webCreate(@RequestBody @Valid WebStudentRequest request) {
        return studentService.webCreate(request);
    }
}
