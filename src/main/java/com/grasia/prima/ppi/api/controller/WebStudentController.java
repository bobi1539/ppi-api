package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.WEB_STUDENT)
@AllArgsConstructor
public class WebStudentController extends BaseController {

    private final StudentService studentService;

    @GetMapping("/count")
    public long countAll() {
        return studentService.countAll();
    }
}
