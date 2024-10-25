package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.GalleryRequest;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.GalleryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GalleryControllerTest extends ControllerTest {

    @InjectMocks
    private GalleryController controller;

    @Mock
    private GalleryService service;

    private final GalleryRequest galleryRequest = ObjectDummy.getGalleryRequest();
    private final GalleryResponse galleryResponse = ObjectDummy.getGalleryResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getGalleryResponses());

        List<GalleryResponse> responses = controller.findAll(1L, "", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<GalleryResponse> getGalleryResponses() {
        return List.of(galleryResponse, galleryResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getGalleryResponsePage());

        Page<GalleryResponse> responses = controller.findAllPagination(1L, "", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<GalleryResponse> getGalleryResponsePage() {
        return new PageImpl<>(getGalleryResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(galleryResponse);

        GalleryResponse response = controller.findById(id);
        assertEquals(galleryResponse.getId(), response.getId());
        assertEquals(galleryResponse.getFileName(), response.getFileName());

        verify(service).findById(id);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(List.of(galleryResponse));

        List<GalleryResponse> responses = controller.create(galleryRequest, header);
        assertNotNull(responses);
        assertEquals(1, responses.size());

        verify(service).create(any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(galleryResponse);

        GalleryResponse response = controller.delete(id, header);
        assertEquals(galleryResponse.getId(), response.getId());
        assertEquals(galleryResponse.getFileName(), response.getFileName());

        verify(service).delete(any(), any());
    }
}