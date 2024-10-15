package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.GalleryRequest;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.dto.search.GallerySearchDto;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.entity.MGallery;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.GalleryRepository;
import com.grasia.prima.ppi.api.service.EventService;
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

class GalleryServiceImplTest extends ServiceTest {

    @InjectMocks
    private GalleryServiceImpl galleryService;

    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private EventService eventService;

    @Mock
    private FileService fileService;

    private final MGallery gallery = ObjectDummy.getGallery();
    private final MEvent event = ObjectDummy.getEvent();
    private final GalleryRequest galleryRequest = ObjectDummy.getGalleryRequest();
    private final GallerySearchDto searchDto = ObjectDummy.getGallerySearchDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(galleryRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getGalleries());

        List<GalleryResponse> responses = galleryService.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(galleryRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MGallery> getGalleries() {
        return List.of(gallery, gallery);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(galleryRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getGalleryPage());

        Page<GalleryResponse> responses = galleryService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(galleryRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MGallery> getGalleryPage() {
        return new PageImpl<>(getGalleries());
    }

    @Test
    void testFindById_Success() {
        when(galleryRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(gallery));

        GalleryResponse response = galleryService.findById(id);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());

        verify(galleryRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testCreate_Success() {
        when(eventService.getEventById(id)).thenReturn(event);
        when(galleryRepository.save(any())).thenReturn(gallery);

        GalleryResponse response = galleryService.create(galleryRequest, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());

        verify(eventService).getEventById(id);
        verify(galleryRepository).save(any());
    }

    @Test
    void testUpdate_FileNotUpdated() {
        when(galleryRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(gallery));
        when(eventService.getEventById(id)).thenReturn(event);
        when(galleryRepository.save(any())).thenReturn(gallery);

        GalleryResponse response = galleryService.update(id, galleryRequest, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());

        verify(galleryRepository).findByIdAndIsDeleted(id, false);
        verify(eventService).getEventById(id);
        verify(galleryRepository).save(any());
    }

    @Test
    void testUpdate_FileUpdated() {
        gallery.setFileName("different-file.png");
        when(galleryRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(gallery));
        when(eventService.getEventById(id)).thenReturn(event);
        when(fileService.saveFileFromBase64(any())).thenReturn("save-file.png");
        when(galleryRepository.save(any())).thenReturn(gallery);

        GalleryResponse response = galleryService.update(id, galleryRequest, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());

        verify(galleryRepository).findByIdAndIsDeleted(id, false);
        verify(eventService).getEventById(id);
        verify(fileService).saveFileFromBase64(any());
        verify(galleryRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(galleryRepository.findById(id)).thenReturn(Optional.of(gallery));
        when(galleryRepository.save(any())).thenReturn(gallery);

        GalleryResponse response = galleryService.delete(id, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());
        assertTrue(response.isDeleted());

        verify(galleryRepository).findById(id);
        verify(galleryRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        gallery.setDeleted(true);
        when(galleryRepository.findById(id)).thenReturn(Optional.of(gallery));

        GalleryResponse response = galleryService.delete(id, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());
        assertTrue(response.isDeleted());

        verify(galleryRepository).findById(id);
        verify(galleryRepository).delete(any());
        verify(fileService).deleteFile(any());
    }

    @Test
    void testRestore_Success() {
        when(galleryRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(gallery));
        when(galleryRepository.save(any())).thenReturn(gallery);

        GalleryResponse response = galleryService.restore(id, header);
        assertEquals(gallery.getId(), response.getId());
        assertEquals(gallery.getFileName(), response.getFileName());
        assertFalse(response.isDeleted());

        verify(galleryRepository).findByIdAndIsDeleted(id, true);
        verify(galleryRepository).save(any());
    }
}