package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebEventControllerTest {

    @InjectMocks
    private WebEventController controller;

    @Mock
    private EventService service;

    private final EventResponse eventResponse = ObjectDummy.getEventResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(new PageImpl<>(List.of(eventResponse, eventResponse)));

        Page<EventResponse> responses = controller.findAllPagination("", 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    @Test
    void testFindBySlug() {
        String slug = "test";
        when(service.findBySlug(slug)).thenReturn(eventResponse);

        EventResponse response = controller.findBySlug(slug);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).findBySlug(slug);
    }
}