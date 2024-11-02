package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.dto.search.StaffSearchDto;
import com.grasia.prima.ppi.api.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.WEB_STAFF)
@AllArgsConstructor
public class WebStaffController extends BaseController {

    private final StaffService staffService;

    @GetMapping("/all")
    public List<StaffResponse> findAll(
            @RequestParam(required = false) Long periodId,
            @RequestParam(required = false) Long divisionId,
            @RequestParam(required = false) Boolean isHead
    ) {
        StaffSearchDto searchDto = StaffSearchDto.builder()
                .periodId(periodId)
                .divisionId(divisionId)
                .isHead(isHead)
                .search(null)
                .isDeleted(false)
                .build();
        return staffService.findAll(searchDto);
    }
}
