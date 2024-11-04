package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebStudentControllerTest {

    @InjectMocks
    private WebStudentController controller;

    @Mock
    private StudentService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCountAll() {
        when(service.countAll()).thenReturn(10L);
        assertEquals(10L, controller.countAll());
        verify(service).countAll();
    }
}