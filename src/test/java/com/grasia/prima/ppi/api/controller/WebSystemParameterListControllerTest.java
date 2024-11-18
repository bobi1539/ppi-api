package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.response.SystemParameterListResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.SystemParameterListService;
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

class WebSystemParameterListControllerTest {

    @InjectMocks
    private WebSystemParameterListController controller;

    @Mock
    private SystemParameterListService parameterListService;

    private final SystemParameterListResponse parameterListResponse = ObjectDummy.getSystemParameterListResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(parameterListService.findAll(any())).thenReturn(List.of(parameterListResponse, parameterListResponse));

        List<SystemParameterListResponse> responses = controller.findAll(1L, "");
        assertEquals(2, responses.size());

        verify(parameterListService).findAll(any());
    }
}