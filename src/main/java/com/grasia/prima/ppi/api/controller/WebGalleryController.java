package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.dto.search.GallerySearchDto;
import com.grasia.prima.ppi.api.service.GalleryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Endpoint.WEB_GALLERY)
@AllArgsConstructor
public class WebGalleryController extends BaseController {

    private final GalleryService galleryService;

    @GetMapping("/all")
    public List<GalleryResponse> findAll(@RequestParam Long eventId) {
        GallerySearchDto searchDto = GallerySearchDto.builder()
                .eventId(eventId)
                .search(null)
                .isDeleted(false)
                .build();
        return galleryService.findAll(searchDto);
    }
}
