package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.NewsletterEmailRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterEmailResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.NewsletterEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebNewsletterEmailControllerTest {

    @InjectMocks
    private WebNewsletterEmailController controller;

    @Mock
    private NewsletterEmailService service;

    private final NewsletterEmailRequest newsletterEmailRequest = ObjectDummy.getNewsletterEmailRequest();
    private final NewsletterEmailResponse newsletterEmailResponse = ObjectDummy.getNewsletterEmailResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(service.create(newsletterEmailRequest)).thenReturn(newsletterEmailResponse);

        NewsletterEmailResponse response = controller.create(newsletterEmailRequest);
        assertEquals(newsletterEmailResponse.getId(), response.getId());
        assertEquals(newsletterEmailResponse.getEmail(), response.getEmail());

        verify(service).create(newsletterEmailRequest);
    }
}