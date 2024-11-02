package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.GalleryService;
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

class WebGalleryControllerTest {

    @InjectMocks
    private WebGalleryController controller;

    @Mock
    private GalleryService service;

    private final GalleryResponse galleryResponse = ObjectDummy.getGalleryResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(List.of(galleryResponse, galleryResponse));

        List<GalleryResponse> responses = controller.findAll(1L);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }
}