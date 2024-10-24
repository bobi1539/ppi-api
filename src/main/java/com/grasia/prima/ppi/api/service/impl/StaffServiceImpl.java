package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.StaffRequest;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.dto.search.StaffSearchDto;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.entity.MStaff;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.StaffHelper;
import com.grasia.prima.ppi.api.repository.StaffRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.DivisionService;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.StaffService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StaffServiceImpl extends AbstractCrudService implements StaffService {

    private final StaffRepository staffRepository;
    private final DivisionService divisionService;
    private final FileService fileService;
    private static final String DIRECTORY_NAME = "staff";

    @Override
    public List<StaffResponse> findAll(StaffSearchDto searchDto) {
        List<MStaff> staffs = staffRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return staffs.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<StaffResponse> findAllPagination(StaffSearchDto searchDto) {
        Page<MStaff> staffs = staffRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return staffs.map(this::toResponse);
    }

    @Override
    public StaffResponse findById(Long id) {
        return toResponse(getStaffById(id));
    }

    @Transactional
    @Override
    public StaffResponse create(StaffRequest request, HeaderRequest header) {
        MStaff staff = MStaff.builder().build();
        setStaff(staff, request);
        setCreatedBy(staff, header);
        setUpdatedBy(staff, header);
        staff.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));

        return toResponse(staffRepository.save(staff));
    }

    @Transactional
    @Override
    public StaffResponse update(Long id, StaffRequest request, HeaderRequest header) {
        MStaff staff = getStaffById(id);
        setStaff(staff, request);
        setUpdatedBy(staff, header);
        saveAndDeletePhoto(staff, request);

        return toResponse(staffRepository.save(staff));
    }

    @Transactional
    @Override
    public StaffResponse delete(Long id, HeaderRequest header) {
        MStaff staff = staffRepository.findById(id).orElseThrow(getNotFoundException());
        if (staff.isDeleted()) {
            staffRepository.delete(staff);
            deleteFile(staff.getPhoto());
        } else {
            staff.setDeleted(true);
            setUpdatedBy(staff, header);
            staff = staffRepository.save(staff);
        }
        return toResponse(staff);
    }

    @Transactional
    @Override
    public StaffResponse restore(Long id, HeaderRequest header) {
        MStaff staff = getStaffDeleted(id);
        staff.setDeleted(false);
        setUpdatedBy(staff, header);

        return toResponse(staffRepository.save(staff));
    }

    @Override
    public MStaff getStaffById(Long id) {
        return staffRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MStaff> getSpecificationFindAll(StaffSearchDto searchDto) {
        Specification<MStaff> spec = SpecificationHelper.stringLike(MStaff.FIELD_NAME, searchDto.getSearch());
        return spec
                .and(SpecificationHelper.entityIdEquals(MStaff.FIELD_DIVISION, searchDto.getDivisionId()))
                .and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setStaff(MStaff staff, StaffRequest request) {
        staff.setName(request.getName());
        staff.setPosition(request.getPosition());
        staff.setIsHead(request.getIsHead());
        staff.setQuote(request.getQuote());
        staff.setFunFact(request.getFunFact());
        staff.setDescription(request.getDescription());
        staff.setJobDescription(request.getJobDescription());
        staff.setDivision(getDivisionById(request.getDivisionId()));
    }

    private MDivision getDivisionById(Long id) {
        return divisionService.getDivisionById(id);
    }

    private void saveAndDeletePhoto(MStaff staff, StaffRequest request) {
        if (!staff.getPhoto().equals(request.getPhoto().getFileName())) {
            deleteFile(staff.getPhoto());
            staff.setPhoto(saveFile(request.getPhoto().getFileName(), request.getPhoto().getFileBase64()));
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
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .build();
        fileService.deleteFile(fileRequest);
    }

    private MStaff getStaffDeleted(Long id) {
        return staffRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private StaffResponse toResponse(MStaff staff) {
        return StaffHelper.toStaffResponse(staff);
    }
}
