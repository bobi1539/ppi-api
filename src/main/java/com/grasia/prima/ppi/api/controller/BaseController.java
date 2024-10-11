package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import com.grasia.prima.ppi.api.dto.response.FileResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.ByteArrayInputStream;

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

    protected ResponseEntity<InputStreamResource> buildResourceResponse(FileResponse response) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, Constant.HEADER_INPUT_STREAM + response.getFileName())
                .contentType(response.getMediaType())
                .body(new InputStreamResource(new ByteArrayInputStream(response.getFileBytes())));
    }

    protected SearchDto buildSearchDto(String search, Boolean isDeleted, int page, int size) {
        return SearchDto.builder()
                .search(search)
                .isDeleted(isDeleted)
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
