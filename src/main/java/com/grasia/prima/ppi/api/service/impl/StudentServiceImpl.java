package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StudentRequest;
import com.grasia.prima.ppi.api.dto.request.WebStudentRequest;
import com.grasia.prima.ppi.api.dto.response.SecretKeyResponse;
import com.grasia.prima.ppi.api.dto.response.StudentResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MStudent;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.repository.StudentRepository;
import com.grasia.prima.ppi.api.service.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class StudentServiceImpl extends AbstractCrudService implements StudentService {

    private final StudentRepository studentRepository;
    private final FileService fileService;
    private final SystemParameterListService parameterListService;
    private final SecretKeyService secretKeyService;
    private static final String DIRECTORY_NAME = "student";
    private static final String STUDENT_FORM_KEY_NAME = "student-form-key";

    @Override
    public List<StudentResponse> findAll(SearchDto searchDto) {
        List<MStudent> students = studentRepository.findAll(getSpecificationFindAll(searchDto), sortByIdDesc());
        return students.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<StudentResponse> findAllPagination(SearchDto searchDto) {
        Page<MStudent> students = studentRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdDesc(searchDto));
        return students.map(this::toResponse);
    }

    @Override
    public StudentResponse findById(Long id) {
        return toResponse(getById(id));
    }

    @Transactional
    @Override
    public StudentResponse create(StudentRequest request, HeaderRequest header) {
        MStudent student = MStudent.builder().build();
        setCreatedBy(student, header);
        setUpdatedBy(student, header);
        setStudent(student, request);
        savePhotoWhenCreate(student, request);
        return toResponse(studentRepository.save(student));
    }

    @Transactional
    @Override
    public StudentResponse update(Long id, StudentRequest request, HeaderRequest header) {
        MStudent student = getById(id);
        setUpdatedBy(student, header);
        setStudent(student, request);
        savePhotoWhenUpdate(student, request);
        return toResponse(studentRepository.save(student));
    }

    @Transactional
    @Override
    public StudentResponse delete(Long id, HeaderRequest header) {
        MStudent student = studentRepository.findById(id).orElseThrow(getNotFoundException());
        if (student.isDeleted()) {
            studentRepository.delete(student);
            deleteFile(student.getPhoto());
        } else {
            student.setDeleted(true);
            setUpdatedBy(student, header);
            student = studentRepository.save(student);
        }
        return toResponse(student);
    }

    @Transactional
    @Override
    public StudentResponse restore(Long id, HeaderRequest header) {
        MStudent student = getStudentDeleted(id);
        student.setDeleted(false);
        setUpdatedBy(student, header);

        return toResponse(studentRepository.save(student));
    }

    @Override
    public MStudent getById(Long id) {
        return studentRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    @Override
    public long countAll() {
        return studentRepository.count();
    }

    @Override
    public SecretKeyResponse generateStudentFormKey() {
        return secretKeyService.generate(STUDENT_FORM_KEY_NAME);
    }

    @Override
    public StudentResponse webCreate(WebStudentRequest request) {
        secretKeyService.verify(request.getSecretKey());
        return create(request, HeaderRequest.builder().build());
    }

    private Specification<MStudent> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MStudent> spec = SpecificationHelper.stringLike(MStudent.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setStudent(MStudent student, StudentRequest request) {
        student.setName(request.getName());
        student.setNickname(request.getNickname());
        student.setEmail(request.getEmail());
        student.setMajor(request.getMajor());
        student.setExpectedGraduationYear(Year.parse(request.getExpectedGraduationYear()));
        student.setBirthDate(request.getBirthDate());
        student.setGender(parameterListService.getSystemParameterListById(request.getGenderId()));
        student.setEducation(parameterListService.getSystemParameterListById(request.getEducationId()));
    }

    private boolean isPhotoRequestNotNull(StudentRequest request) {
        return Objects.nonNull(request.getPhoto());
    }

    private void savePhotoWhenCreate(MStudent student, StudentRequest request) {
        if (isPhotoRequestNotNull(request)) {
            student.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));
        }
    }

    private void savePhotoWhenUpdate(MStudent student, StudentRequest request) {
        if (isPhotoRequestNotNull(request) && !Objects.equals(student.getPhoto(), request.getPhoto().getFileName())) {
            deleteFile(student.getPhoto());
            student.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));
        }
    }

    private String saveFile(String fileName, String base64String) {
        Base64ToFileDto dto = Base64ToFileDto.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .base64String(base64String)
                .build();
        return fileService.saveFileFromBase64(dto);
    }

    private void deleteFile(String fileName) {
        if (Objects.nonNull(fileName)) {
            FileRequest fileRequest = FileRequest.builder()
                    .directoryName(DIRECTORY_NAME)
                    .fileName(fileName)
                    .build();
            fileService.deleteFile(fileRequest);
        }
    }

    private MStudent getStudentDeleted(Long id) {
        return studentRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private StudentResponse toResponse(MStudent student) {
        return StudentResponse.toResponse(student);
    }
}
