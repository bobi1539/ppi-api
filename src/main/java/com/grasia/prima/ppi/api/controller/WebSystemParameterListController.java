package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.dto.search.SystemParameterListSearchDto;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.WEB_SYSTEM_PARAMETER_LIST)
@AllArgsConstructor
public class WebSystemParameterListController {

    private final SystemParameterListService parameterListService;

    @GetMapping("/all")
    public List<SystemParameterListResponse> findAll(
            @RequestParam Long systemParameterId,
            @RequestParam(required = false) String search
    ) {
        SystemParameterListSearchDto searchDto = SystemParameterListSearchDto.builder()
                .systemParameterId(systemParameterId)
                .search(search)
                .build();
        return parameterListService.findAll(searchDto);
    }
}
