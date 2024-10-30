package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.FileUploadRequest;
import com.grasia.prima.ppi.api.dto.request.GalleryRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.dto.search.GallerySearchDto;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.entity.MGallery;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.repository.GalleryRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.EventService;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.GalleryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class GalleryServiceImpl extends AbstractCrudService implements GalleryService {

    private final GalleryRepository galleryRepository;
    private final EventService eventService;
    private final FileService fileService;
    private static final String DIRECTORY_NAME = "gallery";

    @Override
    public List<GalleryResponse> findAll(GallerySearchDto searchDto) {
        List<MGallery> galleries = galleryRepository.findAll(getSpecificationFindAll(searchDto), sortByIdDesc());
        return galleries.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<GalleryResponse> findAllPagination(GallerySearchDto searchDto) {
        Page<MGallery> galleries = galleryRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdDesc(searchDto));
        return galleries.map(this::toResponse);
    }

    @Override
    public GalleryResponse findById(Long id) {
        return toResponse(getGalleryById(id));
    }

    @Transactional
    @Override
    public List<GalleryResponse> create(GalleryRequest request, HeaderRequest header) {
        List<MGallery> galleries = new ArrayList<>();
        for (FileUploadRequest fileUpload : request.getFileUploads()) {
            MGallery gallery = MGallery.builder().build();
            gallery.setEvent(getEventById(request.getEventId()));
            gallery.setFileName(saveFile(fileUpload.getFileName(), fileUpload.getFileBase64()));
            setCreatedBy(gallery, header);
            setUpdatedBy(gallery, header);
            galleries.add(gallery);
        }
        galleries = galleryRepository.saveAll(galleries);

        return galleries.stream().map(this::toResponse).toList();
    }

    @Transactional
    @Override
    public GalleryResponse delete(Long id, HeaderRequest header) {
        MGallery gallery = galleryRepository.findById(id).orElseThrow(getNotFoundException());
        galleryRepository.delete(gallery);
        deleteFile(gallery.getFileName());
        return toResponse(gallery);
    }

    @Override
    public MGallery getGalleryById(Long id) {
        return galleryRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MGallery> getSpecificationFindAll(GallerySearchDto searchDto) {
        Specification<MGallery> spec = SpecificationHelper.entityIdEquals(MGallery.FIELD_EVENT, searchDto.getEventId());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private MEvent getEventById(Long id) {
        return eventService.getEventById(id);
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

    private GalleryResponse toResponse(MGallery gallery) {
        return GalleryResponse.toResponse(gallery);
    }
}
