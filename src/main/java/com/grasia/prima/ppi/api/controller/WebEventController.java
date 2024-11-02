package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.WEB_EVENT)
@AllArgsConstructor
public class WebEventController extends BaseController {

    private final EventService eventService;

    @GetMapping
    public Page<EventResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return eventService.findAllPagination(buildSearchDto(search, false, page, size));
    }

    @GetMapping("/slug/{slug}")
    public EventResponse findBySlug(@PathVariable String slug) {
        return eventService.findBySlug(slug);
    }
}
