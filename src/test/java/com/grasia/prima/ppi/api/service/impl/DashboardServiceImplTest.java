package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.response.DashboardResponse;
import com.grasia.prima.ppi.api.service.EventService;
import com.grasia.prima.ppi.api.service.GalleryService;
import com.grasia.prima.ppi.api.service.NewsletterService;
import com.grasia.prima.ppi.api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DashboardServiceImplTest {

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    @Mock
    private StudentService studentService;

    @Mock
    private EventService eventService;

    @Mock
    private NewsletterService newsletterService;

    @Mock
    private GalleryService galleryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboard() {
        when(studentService.countAll()).thenReturn(10L);
        when(eventService.countAll()).thenReturn(10L);
        when(newsletterService.countAll()).thenReturn(10L);
        when(galleryService.countAll()).thenReturn(10L);

        DashboardResponse response = dashboardService.getDashboard();
        assertEquals(10L, response.getTotalStudent());
        assertEquals(10L, response.getTotalEvent());
        assertEquals(10L, response.getTotalNewsletter());
        assertEquals(10L, response.getTotalGallery());

        verify(studentService).countAll();
        verify(eventService).countAll();
        verify(newsletterService).countAll();
        verify(galleryService).countAll();
    }
}