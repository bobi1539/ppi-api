package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.EventRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.service.EventService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.EVENT)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class EventController extends BaseController {

    private final EventService eventService;

    @GetMapping("/all")
    public List<EventResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return eventService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<EventResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return eventService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public EventResponse findById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @GetMapping("/slug/{slug}")
    public EventResponse findBySlug(@PathVariable String slug) {
        return eventService.findBySlug(slug);
    }

    @PostMapping
    public EventResponse create(
            @RequestBody @Valid EventRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return eventService.create(request, header);
    }

    @PutMapping("/{id}")
    public EventResponse update(
            @PathVariable Long id,
            @RequestBody @Valid EventRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return eventService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public EventResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return eventService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public EventResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return eventService.restore(id, header);
    }
}
