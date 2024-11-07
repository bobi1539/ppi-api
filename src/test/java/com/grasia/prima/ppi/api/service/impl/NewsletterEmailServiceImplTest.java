package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.NewsletterEmailRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterEmailResponse;
import com.grasia.prima.ppi.api.entity.TNewsletterEmail;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.NewsletterEmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsletterEmailServiceImplTest {

    @InjectMocks
    private NewsletterEmailServiceImpl newsletterEmailService;

    @Mock
    private NewsletterEmailRepository newsletterEmailRepository;

    private final NewsletterEmailRequest newsletterEmailRequest = ObjectDummy.getNewsletterEmailRequest();
    private final TNewsletterEmail newsletterEmail = ObjectDummy.getNewsletterEmail();
    private final String email = newsletterEmailRequest.getEmail();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() {
        when(newsletterEmailRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(newsletterEmailRepository.save(any())).thenReturn(newsletterEmail);

        NewsletterEmailResponse response = newsletterEmailService.create(newsletterEmailRequest);
        assertEquals(newsletterEmail.getId(), response.getId());
        assertEquals(newsletterEmail.getEmail(), response.getEmail());

        verify(newsletterEmailRepository).findByEmail(email);
        verify(newsletterEmailRepository).save(any());
    }

    @Test
    void testCreate_Failed() {
        when(newsletterEmailRepository.findByEmail(email)).thenReturn(Optional.of(newsletterEmail));

        BusinessException e = assertThrows(
                BusinessException.class, () -> newsletterEmailService.create(newsletterEmailRequest)
        );
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE.status, e.getStatus());
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE.message, e.getMessage());

        verify(newsletterEmailRepository).findByEmail(email);
    }
}