package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.DepartmentRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.DepartmentResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MDepartment;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.DepartmentHelper;
import com.grasia.prima.ppi.api.repository.DepartmentRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.DepartmentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DepartmentServiceImpl extends AbstractCrudService implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentResponse> findAll(SearchDto searchDto) {
        List<MDepartment> departments = departmentRepository
                .findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return departments.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<DepartmentResponse> findAllPagination(SearchDto searchDto) {
        Page<MDepartment> departments = departmentRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return departments.map(this::toResponse);
    }

    @Override
    public DepartmentResponse findById(Long id) {
        MDepartment department = getDepartmentById(id);
        return toResponse(department);
    }

    @Transactional
    @Override
    public DepartmentResponse create(DepartmentRequest request, HeaderRequest header) {
        MDepartment department = MDepartment.builder().build();
        setDepartment(department, request);
        setCreatedBy(department, header);
        setUpdatedBy(department, header);

        department = departmentRepository.save(department);
        return toResponse(department);
    }

    @Transactional
    @Override
    public DepartmentResponse update(Long id, DepartmentRequest request, HeaderRequest header) {
        MDepartment department = getDepartmentById(id);
        setDepartment(department, request);
        setUpdatedBy(department, header);

        department = departmentRepository.save(department);
        return toResponse(department);
    }

    @Transactional
    @Override
    public DepartmentResponse delete(Long id, HeaderRequest header) {
        MDepartment department = departmentRepository.findById(id).orElseThrow(getNotFoundException());
        if (department.isDeleted()) {
            departmentRepository.delete(department);
        } else {
            department.setDeleted(true);
            setUpdatedBy(department, header);
            department = departmentRepository.save(department);
        }
        return toResponse(department);
    }

    @Transactional
    @Override
    public DepartmentResponse restore(Long id, HeaderRequest header) {
        MDepartment department = getDepartmentDeleted(id);
        department.setDeleted(false);
        setUpdatedBy(department, header);

        department = departmentRepository.save(department);
        return toResponse(department);
    }

    @Override
    public MDepartment getDepartmentById(Long id) {
        return departmentRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MDepartment> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MDepartment> spec = SpecificationHelper.stringLike(MDepartment.FIELD_NAME, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setDepartment(MDepartment department, DepartmentRequest request) {
        department.setName(request.getName());
    }

    private MDepartment getDepartmentDeleted(Long id) {
        return departmentRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private DepartmentResponse toResponse(MDepartment department) {
        return DepartmentHelper.toDepartmentResponse(department);
    }
}
