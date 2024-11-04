package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StudentRequest;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MStudent;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    List<StudentResponse> findAll(SearchDto searchDto);

    Page<StudentResponse> findAllPagination(SearchDto searchDto);

    StudentResponse findById(Long id);

    StudentResponse create(StudentRequest request, HeaderRequest header);

    StudentResponse update(Long id, StudentRequest request, HeaderRequest header);

    StudentResponse delete(Long id, HeaderRequest header);

    StudentResponse restore(Long id, HeaderRequest header);

    MStudent getById(Long id);

    long countAll();
}
