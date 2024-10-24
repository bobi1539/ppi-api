package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.StaffRequest;
import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.dto.search.StaffSearchDto;
import com.grasia.prima.ppi.api.entity.MDivision;
import com.grasia.prima.ppi.api.entity.MStaff;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.StaffRepository;
import com.grasia.prima.ppi.api.service.DivisionService;
import com.grasia.prima.ppi.api.service.FileService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StaffServiceImplTest extends ServiceTest {

    @InjectMocks
    private StaffServiceImpl staffService;

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private DivisionService divisionService;

    @Mock
    private FileService fileService;

    private final MStaff staff = ObjectDummy.getStaff();
    private final MDivision division = ObjectDummy.getDivision();
    private final StaffRequest staffRequest = ObjectDummy.getStaffRequest();
    private final StaffSearchDto searchDto = ObjectDummy.getStaffSearchDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(staffRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getStaffs());

        List<StaffResponse> responses = staffService.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(staffRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MStaff> getStaffs() {
        return List.of(staff, staff);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(staffRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getStaffPage());

        Page<StaffResponse> responses = staffService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(staffRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MStaff> getStaffPage() {
        return new PageImpl<>(getStaffs());
    }

    @Test
    void testFindById_Success() {
        when(staffRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(staff));

        StaffResponse response = staffService.findById(id);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());

        verify(staffRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindById_NotFound() {
        when(staffRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> staffService.findById(id));
        assertEquals(GlobalMessage.DATA_NOT_FOUND.status, e.getStatus());
        assertEquals(GlobalMessage.DATA_NOT_FOUND.message, e.getMessage());

        verify(staffRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(divisionService.getDivisionById(id)).thenReturn(division);
        when(fileService.saveFileFromBase64(any())).thenReturn("test.png");
        when(staffRepository.save(any())).thenReturn(staff);

        StaffResponse response = staffService.create(staffRequest, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());

        verify(divisionService).getDivisionById(id);
        verify(fileService).saveFileFromBase64(any());
        verify(staffRepository).save(any());
    }

    @Test
    void testUpdate_Success() {
        mockUpdate();

        StaffResponse response = staffService.update(id, staffRequest, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());

        verifyUpdate();
    }

    @Test
    void testUpdate_SuccessWithDifferentFileName() {
        staffRequest.getPhoto().setFileName("different-name.png");
        mockUpdate();

        StaffResponse response = staffService.update(id, staffRequest, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());

        verifyUpdate();
        verify(fileService).saveFileFromBase64(any());
    }

    private void mockUpdate() {
        when(staffRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(staff));
        when(divisionService.getDivisionById(id)).thenReturn(division);
        when(fileService.saveFileFromBase64(any())).thenReturn("test.png");
        when(staffRepository.save(any())).thenReturn(staff);
    }

    private void verifyUpdate() {
        verify(staffRepository).findByIdAndIsDeleted(id, false);
        verify(divisionService).getDivisionById(id);
        verify(staffRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(staffRepository.findById(id)).thenReturn(Optional.of(staff));
        when(staffRepository.save(any())).thenReturn(staff);

        StaffResponse response = staffService.delete(id, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(staffRepository).findById(id);
        verify(staffRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        staff.setDeleted(true);
        when(staffRepository.findById(id)).thenReturn(Optional.of(staff));

        StaffResponse response = staffService.delete(id, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());
        assertTrue(response.isDeleted());

        verify(staffRepository).findById(id);
        verify(staffRepository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(staffRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(staff));
        when(staffRepository.save(any())).thenReturn(staff);

        StaffResponse response = staffService.restore(id, header);
        assertEquals(staff.getId(), response.getId());
        assertEquals(staff.getName(), response.getName());
        assertFalse(response.isDeleted());

        verify(staffRepository).findByIdAndIsDeleted(id, true);
        verify(staffRepository).save(any());
    }
}