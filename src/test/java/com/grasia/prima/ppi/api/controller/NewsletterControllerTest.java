package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.NewsletterRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.NewsletterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NewsletterControllerTest extends ControllerTest {

    @InjectMocks
    private NewsletterController controller;

    @Mock
    private NewsletterService service;

    private final NewsletterRequest newsletterRequest = ObjectDummy.getNewsletterRequest();
    private final NewsletterResponse newsletterResponse = ObjectDummy.getNewsletterResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getNewsletterResponses());

        List<NewsletterResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<NewsletterResponse> getNewsletterResponses() {
        return List.of(newsletterResponse, newsletterResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getNewsletterResponsePage());

        Page<NewsletterResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<NewsletterResponse> getNewsletterResponsePage() {
        return new PageImpl<>(getNewsletterResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.findById(id);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).findById(id);
    }

    @Test
    void testFindBySlug() {
        String slug = "test";
        when(service.findBySlug(slug)).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.findBySlug(slug);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).findBySlug(slug);
    }

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.create(newsletterRequest, header);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.update(id, newsletterRequest, header);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.delete(id, header);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(newsletterResponse);

        NewsletterResponse response = controller.restore(id, header);
        assertEquals(newsletterResponse.getId(), response.getId());
        assertEquals(newsletterResponse.getTitle(), response.getTitle());

        verify(service).restore(any(), any());
    }

    @Test
    void testResendEmail() {
        assertDoesNotThrow(() -> controller.resendEmail(id));
        verify(service).resendEmail(id);
    }
}