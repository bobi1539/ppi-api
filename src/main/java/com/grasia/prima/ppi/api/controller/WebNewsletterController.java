package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.NewsletterSubscriptionRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.dto.response.NewsletterSubscriptionResponse;
import com.grasia.prima.ppi.api.service.NewsletterService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.WEB_NEWSLETTER)
@AllArgsConstructor
public class WebNewsletterController extends BaseController {

    private final NewsletterService newsletterService;

    @GetMapping
    public Page<NewsletterResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return newsletterService.findAllPagination(buildSearchDto(search, false, page, size));
    }

    @GetMapping("/slug/{slug}")
    public NewsletterResponse findBySlug(@PathVariable String slug) {
        return newsletterService.findBySlug(slug);
    }

    @GetMapping("/count")
    public long countAll() {
        return newsletterService.countAll();
    }

    @PostMapping("/subscribe")
    public NewsletterSubscriptionResponse subscribe(@RequestBody @Valid NewsletterSubscriptionRequest request) {
        return newsletterService.subscribe(request);
    }
}
