package com.grasia.prima.ppi.api.controller;

import com.grasia.prima.ppi.api.dto.request.EventRequest;
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

class EventControllerTest extends ControllerTest {

    @InjectMocks
    private EventController controller;

    @Mock
    private EventService service;

    private final EventRequest eventRequest = ObjectDummy.getEventRequest();
    private final EventResponse eventResponse = ObjectDummy.getEventResponse();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        when(service.findAll(any())).thenReturn(getEventResponses());

        List<EventResponse> responses = controller.findAll("", null);
        assertEquals(2, responses.size());

        verify(service).findAll(any());
    }

    private List<EventResponse> getEventResponses() {
        return List.of(eventResponse, eventResponse);
    }

    @Test
    void testFindAllPagination() {
        when(service.findAllPagination(any())).thenReturn(getEventResponsePage());

        Page<EventResponse> responses = controller.findAllPagination("", null, 1, 10);
        assertEquals(2, responses.getTotalElements());

        verify(service).findAllPagination(any());
    }

    private Page<EventResponse> getEventResponsePage() {
        return new PageImpl<>(getEventResponses());
    }

    @Test
    void testFindById() {
        when(service.findById(id)).thenReturn(eventResponse);

        EventResponse response = controller.findById(id);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).findById(id);
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

    @Test
    void testCreate() {
        when(service.create(any(), any())).thenReturn(eventResponse);

        EventResponse response = controller.create(eventRequest, header);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).create(any(), any());
    }

    @Test
    void testUpdate() {
        when(service.update(any(), any(), any())).thenReturn(eventResponse);

        EventResponse response = controller.update(id, eventRequest, header);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).update(any(), any(), any());
    }

    @Test
    void testDelete() {
        when(service.delete(any(), any())).thenReturn(eventResponse);

        EventResponse response = controller.delete(id, header);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).delete(any(), any());
    }

    @Test
    void testRestore() {
        when(service.restore(any(), any())).thenReturn(eventResponse);

        EventResponse response = controller.restore(id, header);
        assertEquals(eventResponse.getId(), response.getId());
        assertEquals(eventResponse.getTitle(), response.getTitle());

        verify(service).restore(any(), any());
    }
}