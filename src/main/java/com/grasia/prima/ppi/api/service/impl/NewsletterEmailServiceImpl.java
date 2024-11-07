package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.NewsletterEmailRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterEmailResponse;
import com.grasia.prima.ppi.api.entity.TNewsletterEmail;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.repository.NewsletterEmailRepository;
import com.grasia.prima.ppi.api.service.NewsletterEmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class NewsletterEmailServiceImpl implements NewsletterEmailService {

    private final NewsletterEmailRepository newsletterEmailRepository;

    @Override
    public NewsletterEmailResponse create(NewsletterEmailRequest request) {
        validateRequest(request);

        TNewsletterEmail newsletterEmail = TNewsletterEmail.builder()
                .email(request.getEmail())
                .build();
        return toResponse(newsletterEmailRepository.save(newsletterEmail));
    }

    private void validateRequest(NewsletterEmailRequest request) {
        Optional<TNewsletterEmail> email = newsletterEmailRepository.findByEmail(request.getEmail());
        if (email.isPresent()) {
            throw new BusinessException(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE);
        }
    }

    public NewsletterEmailResponse toResponse(TNewsletterEmail newsletterEmail) {
        return NewsletterEmailResponse.toResponse(newsletterEmail);
    }
}
