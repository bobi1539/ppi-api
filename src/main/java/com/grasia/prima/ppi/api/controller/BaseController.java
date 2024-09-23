package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Slf4j
public abstract class BaseController {

    protected <T> BaseResponse<T> buildSuccessResponse(T data) {
        return BaseResponse.<T>builder()
                .code(GlobalMessage.SUCCESS.status.value())
                .message(GlobalMessage.SUCCESS.message)
                .data(data)
                .build();
    }

    protected SearchDto buildSearchDto(String search) {
        return SearchDto.builder().search(search).build();
    }

    protected PageDto buildPageDto(String search, int page, int size) {
        return PageDto.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();
    }

    @ModelAttribute(name = Constant.HEADER)
    public HeaderRequest buildHeader(HttpServletRequest request) {
        log.info("incoming request. method : {}, endpoint : {}", request.getMethod(), request.getRequestURI());
        log.info("query param : {}", request.getQueryString());

        return (HeaderRequest) request.getAttribute(Constant.HEADER);
    }
}
