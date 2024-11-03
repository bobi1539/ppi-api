package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.EventRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MEvent;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    List<EventResponse> findAll(SearchDto searchDto);

    Page<EventResponse> findAllPagination(SearchDto searchDto);

    EventResponse findById(Long id);

    EventResponse findBySlug(String slug);

    EventResponse create(EventRequest request, HeaderRequest header);

    EventResponse update(Long id, EventRequest request, HeaderRequest header);

    EventResponse delete(Long id, HeaderRequest header);

    EventResponse restore(Long id, HeaderRequest header);

    MEvent getEventById(Long id);

    long countAll();
}
