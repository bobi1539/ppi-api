package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.GalleryRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.GalleryResponse;
import com.grasia.prima.ppi.api.dto.search.GallerySearchDto;
import com.grasia.prima.ppi.api.service.GalleryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.GALLERY)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class GalleryController {

    private final GalleryService galleryService;

    @GetMapping("/all")
    public List<GalleryResponse> findAll(
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        GallerySearchDto searchDto = GallerySearchDto.builder()
                .eventId(eventId)
                .search(search)
                .isDeleted(isDeleted)
                .build();
        return galleryService.findAll(searchDto);
    }

    @GetMapping
    public Page<GalleryResponse> findAllPagination(
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        GallerySearchDto searchDto = GallerySearchDto.builder()
                .eventId(eventId)
                .search(search)
                .isDeleted(isDeleted)
                .page(page)
                .size(size)
                .build();
        return galleryService.findAllPagination(searchDto);
    }

    @GetMapping("/{id}")
    public GalleryResponse findById(@PathVariable Long id) {
        return galleryService.findById(id);
    }

    @PostMapping
    public GalleryResponse create(
            @RequestBody @Valid GalleryRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return galleryService.create(request, header);
    }

    @PutMapping("/{id}")
    public GalleryResponse update(
            @PathVariable Long id,
            @RequestBody @Valid GalleryRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return galleryService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public GalleryResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return galleryService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public GalleryResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return galleryService.restore(id, header);
    }
}
