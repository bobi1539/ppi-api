package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Constant;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.NewsletterRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.service.NewsletterService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.NEWSLETTER)
@AllArgsConstructor
@SecurityRequirement(name = Constant.AUTHORIZATION)
public class NewsletterController extends BaseController {

    private final NewsletterService newsletterService;

    @GetMapping("/all")
    public List<NewsletterResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted
    ) {
        return newsletterService.findAll(buildSearchDto(search, isDeleted, 0, 0));
    }

    @GetMapping
    public Page<NewsletterResponse> findAllPagination(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isDeleted,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return newsletterService.findAllPagination(buildSearchDto(search, isDeleted, page, size));
    }

    @GetMapping("/{id}")
    public NewsletterResponse findById(@PathVariable Long id) {
        return newsletterService.findById(id);
    }

    @GetMapping("/slug/{slug}")
    public NewsletterResponse findBySlug(@PathVariable String slug) {
        return newsletterService.findBySlug(slug);
    }

    @PostMapping
    public NewsletterResponse create(
            @RequestBody @Valid NewsletterRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return newsletterService.create(request, header);
    }

    @PutMapping("/{id}")
    public NewsletterResponse update(
            @PathVariable Long id,
            @RequestBody @Valid NewsletterRequest request,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return newsletterService.update(id, request, header);
    }

    @DeleteMapping("/{id}")
    public NewsletterResponse delete(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return newsletterService.delete(id, header);
    }

    @PutMapping("/restore/{id}")
    public NewsletterResponse restore(
            @PathVariable Long id,
            @Parameter(hidden = true) @ModelAttribute(name = Constant.HEADER) HeaderRequest header
    ) {
        return newsletterService.restore(id, header);
    }

    @PostMapping("/resend-email/{id}")
    public void resendEmail(@PathVariable Long id) {
        newsletterService.resendEmail(id);
    }
}
