package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.response.BaseResponse;
import com.grasia.prima.ppi.api.constant.GlobalMessage;

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
}
