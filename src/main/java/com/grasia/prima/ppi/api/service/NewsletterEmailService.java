package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.NewsletterEmailRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterEmailResponse;

public interface NewsletterEmailService {

    NewsletterEmailResponse create(NewsletterEmailRequest request);
}
