package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.GalleryRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.dto.search.GallerySearchDto;
import com.grasia.prima.ppi.api.entity.MGallery;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GalleryService {

    List<GalleryResponse> findAll(GallerySearchDto searchDto);

    Page<GalleryResponse> findAllPagination(GallerySearchDto searchDto);

    GalleryResponse findById(Long id);

    GalleryResponse create(GalleryRequest request, HeaderRequest header);

    GalleryResponse update(Long id, GalleryRequest request, HeaderRequest header);

    GalleryResponse delete(Long id, HeaderRequest header);

    GalleryResponse restore(Long id, HeaderRequest header);

    MGallery getGalleryById(Long id);
}
