package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.response.StaffResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebStaffControllerTest {

    @InjectMocks
    private WebStaffController controller;

    @Mock
    private StaffService service;

    private final StaffResponse staffResponse = ObjectDummy.getStaffResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(List.of(staffResponse, staffResponse));

        List<StaffResponse> responses = controller.findAll(1L, null, null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }
}