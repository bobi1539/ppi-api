package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.response.DashboardResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DashboardControllerTest {

    @InjectMocks
    private DashboardController controller;

    @Mock
    private DashboardService service;

    private final DashboardResponse dashboardResponse = ObjectDummy.getDashboardResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboard() {
        when(service.getDashboard(2024)).thenReturn(dashboardResponse);
        DashboardResponse response = controller.getDashboard(2024);
        assertEquals(10L, response.getTotalStudent());
        assertEquals(10L, response.getTotalEvent());
        assertEquals(10L, response.getTotalNewsletter());
        assertEquals(10L, response.getTotalGallery());
        verify(service).getDashboard(2024);
    }
}