package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.dto.request.NewsletterEmailRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterEmailResponse;
import com.grasia.prima.ppi.api.service.NewsletterEmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.WEB_NEWSLETTER_EMAIL)
@AllArgsConstructor
public class WebNewsletterEmailController {

    private final NewsletterEmailService newsletterEmailService;

    @PostMapping
    public NewsletterEmailResponse create(@RequestBody @Valid NewsletterEmailRequest request) {
        return newsletterEmailService.create(request);
    }
}
