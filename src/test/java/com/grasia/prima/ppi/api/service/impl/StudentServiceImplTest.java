package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.FileUploadRequest;
import com.grasia.prima.ppi.api.dto.request.StudentRequest;
import com.grasia.prima.ppi.api.dto.request.WebStudentRequest;
import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.entity.MStudent;
import com.grasia.prima.ppi.api.entity.MSystemParameterList;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.StudentRepository;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.SecretKeyService;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceImplTest extends ServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FileService fileService;

    @Mock
    private SecretKeyService secretKeyService;

    @Mock
    private SystemParameterListService parameterListService;

    private final MStudent student = ObjectDummy.getStudent();
    private final StudentRequest request = ObjectDummy.getStudentRequest();
    private final MSystemParameterList parameterList = ObjectDummy.getSystemParameterList();
    private final SecretKeyResponse secretKeyResponse = ObjectDummy.getSecretKeyResponse();
    private final WebStudentRequest webStudentRequest = ObjectDummy.getWebStudentRequest();

    private final String fileName = "file.png";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(studentRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getStudents());

        List<StudentResponse> responses = studentService.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(studentRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MStudent> getStudents() {
        return List.of(student, student);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(studentRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getStudentPage());

        Page<StudentResponse> responses = studentService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(studentRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MStudent> getStudentPage() {
        return new PageImpl<>(getStudents());
    }

    @Test
    void testFindById_Success() {
        when(studentRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(student));

        StudentResponse response = studentService.findById(id);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(studentRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_PhotoIsNotNull() {
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(fileService.saveFileFromBase64(any())).thenReturn(fileName);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.create(request, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(fileService).saveFileFromBase64(any());
        verify(studentRepository).save(any());
    }

    @Test
    void testCreate_PhotoIsNull() {
        request.setPhoto(null);

        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.create(request, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdate_PhotoRequestIsNull() {
        request.setPhoto(null);
        when(studentRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(student));
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.update(id, request, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(studentRepository).findByIdAndIsDeleted(id, false);
        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdate_PhotoNotUpdated() {
        FileUploadRequest photoRequest = ObjectDummy.getFileUploadRequest();
        photoRequest.setFileName(student.getPhoto());
        request.setPhoto(photoRequest);

        when(studentRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(student));
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.update(id, request, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(studentRepository).findByIdAndIsDeleted(id, false);
        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(studentRepository).save(any());
    }

    @Test
    void testUpdate_PhotoIsNotNullAndPhotoUpdated() {
        FileUploadRequest photoRequest = ObjectDummy.getFileUploadRequest();
        photoRequest.setFileName("different-photo.png");
        request.setPhoto(photoRequest);

        when(studentRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(student));
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(fileService.saveFileFromBase64(any())).thenReturn(fileName);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.update(id, request, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(studentRepository).findByIdAndIsDeleted(id, false);
        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(fileService).saveFileFromBase64(any());
        verify(fileService).deleteFile(any());
        verify(studentRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.delete(id, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(studentRepository).findById(id);
        verify(studentRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrueAndPhotoIsNotNull() {
        testDelete_IsDeletedTrue();
        verify(fileService).deleteFile(any());
    }

    @Test
    void testDelete_IsDeletedTrueAndPhotoIsNull() {
        student.setPhoto(null);
        testDelete_IsDeletedTrue();
    }

    void testDelete_IsDeletedTrue() {
        student.setDeleted(true);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        StudentResponse response = studentService.delete(id, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(studentRepository).findById(id);
        verify(studentRepository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(studentRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.restore(id, header);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(studentRepository).findByIdAndIsDeleted(id, true);
        verify(studentRepository).save(any());
    }

    @Test
    void testCountAll() {
        when(studentRepository.count()).thenReturn(10L);
        assertEquals(10L, studentService.countAll());
        verify(studentRepository).count();
    }

    @Test
    void testGenerateStudentFormKey() {
        when(secretKeyService.generate(any())).thenReturn(secretKeyResponse);

        SecretKeyResponse response = studentService.generateStudentFormKey();
        assertEquals(secretKeyResponse.getName(), response.getName());
        assertEquals(secretKeyResponse.getKey(), response.getKey());

        verify(secretKeyService).generate(any());
    }

    @Test
    void testWebCreate() {
        when(parameterListService.getSystemParameterListById(id)).thenReturn(parameterList);
        when(fileService.saveFileFromBase64(any())).thenReturn(fileName);
        when(studentRepository.save(any())).thenReturn(student);

        StudentResponse response = studentService.webCreate(webStudentRequest);
        assertEquals(student.getId(), response.getId());
        assertEquals(student.getName(), response.getName());

        verify(parameterListService, times(2)).getSystemParameterListById(id);
        verify(fileService).saveFileFromBase64(any());
        verify(studentRepository).save(any());
    }
}