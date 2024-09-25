package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.PageDto;
import com.grasia.prima.ppi.api.dto.SearchDto;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<T, R> {

    List<R> findAll(SearchDto searchDto);

    Page<R> findAllPagination(PageDto pageDto);

    R findById(Long id);

    R create(T request, HeaderRequest header);

    R update(Long id, T request, HeaderRequest header);

    R delete(Long id, HeaderRequest header);
}
