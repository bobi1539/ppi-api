package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CrudService<T, R, S> {

    List<R> findAll(S searchDto);

    Page<R> findAllPagination(S searchDto);

    R findById(Long id);

    R create(T request, HeaderRequest header);

    R update(Long id, T request, HeaderRequest header);

    R delete(Long id, HeaderRequest header);
}
