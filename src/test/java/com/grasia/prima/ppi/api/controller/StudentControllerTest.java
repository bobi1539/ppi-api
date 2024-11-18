package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.StudentRequest;
import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentControllerTest extends ControllerTest {

    @InjectMocks
    private StudentController controller;

    @Mock
    private StudentService service;

    private final StudentRequest studentRequest = ObjectDummy.getStudentRequest();
    private final StudentResponse studentResponse = ObjectDummy.getStudentResponse();
    private final SecretKeyResponse secretKeyResponse = ObjectDummy.getSecretKeyResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getStudentResponses());

        List<StudentResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<StudentResponse> getStudentResponses() {
        return List.of(studentResponse, studentResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getStudentResponsePage());

        Page<StudentResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<StudentResponse> getStudentResponsePage() {
        return new PageImpl<>(getStudentResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(studentResponse);

        StudentResponse response = controller.findById(id);
        assertEquals(studentResponse.getId(), response.getId());
        assertEquals(studentResponse.getName(), response.getName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(studentResponse);

        StudentResponse response = controller.create(studentRequest, header);
        assertEquals(studentResponse.getId(), response.getId());
        assertEquals(studentResponse.getName(), response.getName());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(studentResponse);

        StudentResponse response = controller.update(id, studentRequest, header);
        assertEquals(studentResponse.getId(), response.getId());
        assertEquals(studentResponse.getName(), response.getName());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(studentResponse);

        StudentResponse response = controller.delete(id, header);
        assertEquals(studentResponse.getId(), response.getId());
        assertEquals(studentResponse.getName(), response.getName());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(studentResponse);

        StudentResponse response = controller.restore(id, header);
        assertEquals(studentResponse.getId(), response.getId());
        assertEquals(studentResponse.getName(), response.getName());

        verify(service).restore(any(), any());
    }

    @Test
    void testGetStudentFormKey() {
        when(service.getStudentFormKey()).thenReturn(secretKeyResponse);

        SecretKeyResponse response = controller.getStudentFormKey();
        assertEquals(secretKeyResponse.getName(), response.getName());
        assertEquals(secretKeyResponse.getKey(), response.getKey());

        verify(service).getStudentFormKey();
    }

    @Test
    void testGenerateStudentFormKey() {
        when(service.generateStudentFormKey()).thenReturn(secretKeyResponse);

        SecretKeyResponse response = controller.generateStudentFormKey();
        assertEquals(secretKeyResponse.getName(), response.getName());
        assertEquals(secretKeyResponse.getKey(), response.getKey());

        verify(service).generateStudentFormKey();
    }
}