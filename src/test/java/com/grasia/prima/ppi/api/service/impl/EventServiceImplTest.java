package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.EventRequest;
import com.grasia.prima.ppi.api.dto.response.EventPerMonthResponse;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.EventRepository;
import com.grasia.prima.ppi.api.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventServiceImplTest extends ServiceTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FileService fileService;

    private final MEvent event = ObjectDummy.getEvent();
    private final EventRequest eventRequest = ObjectDummy.getEventRequest();
    private final String slug = "test";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(eventRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getEvents());

        List<EventResponse> responses = eventService.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(eventRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MEvent> getEvents() {
        return List.of(event, event);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(eventRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getEventPage());

        Page<EventResponse> responses = eventService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(eventRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MEvent> getEventPage() {
        return new PageImpl<>(getEvents());
    }

    @Test
    void testFindById_Success() {
        when(eventRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(event));

        EventResponse response = eventService.findById(id);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindBySlug_Success() {
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.of(event));

        EventResponse response = eventService.findBySlug(slug);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findBySlug(slug);
    }

    @Test
    void testCreate_EventBySlugIsEmpty() {
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.create(eventRequest, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findBySlug(slug);
        verify(eventRepository).save(any());
    }

    @Test
    void testCreate_EventBySlugIsPresent() {
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.of(event));

        BusinessException e = assertThrows(BusinessException.class, () -> eventService.create(eventRequest, header));
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.status, e.getStatus());
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.message, e.getMessage());

        verify(eventRepository).findBySlug(slug);
    }

    @Test
    void testCreate_StartEndDateNotValid() {
        eventRequest.setStartDate(LocalDate.of(2024, 10, 10));
        eventRequest.setEndDate(LocalDate.of(2024, 9, 10));

        BusinessException e = assertThrows(BusinessException.class, () -> eventService.create(eventRequest, header));
        assertEquals(GlobalMessage.START_END_DATE_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.START_END_DATE_NOT_VALID.message, e.getMessage());
    }

    @Test
    void testCreate_StartEndTimeNotValid() {
        eventRequest.setStartTime(LocalTime.of(15, 0, 0));
        eventRequest.setEndTime(LocalTime.of(12, 0, 0));

        BusinessException e = assertThrows(BusinessException.class, () -> eventService.create(eventRequest, header));
        assertEquals(GlobalMessage.START_END_TIME_NOT_VALID.status, e.getStatus());
        assertEquals(GlobalMessage.START_END_TIME_NOT_VALID.message, e.getMessage());
    }

    @Test
    void testUpdate_EventBySlugIsEmpty() {
        when(eventRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(event));
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.update(id, eventRequest, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findByIdAndIsDeleted(id, false);
        verify(eventRepository).findBySlug(slug);
        verify(eventRepository).save(any());
    }

    @Test
    void testUpdate_EventBySlugIsPresentAndSlugIsEqualsWithExisting() {
        when(eventRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(event));
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.update(id, eventRequest, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findByIdAndIsDeleted(id, false);
        verify(eventRepository).findBySlug(slug);
        verify(eventRepository).save(any());
    }

    @Test
    void testUpdate_EventBySlugIsPresentAndSlugIsDifferentWithExisting() {
        MEvent different = ObjectDummy.getEvent();
        different.setId(100L);
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.of(different));
        when(eventRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(event));

        BusinessException e = assertThrows(BusinessException.class, () -> eventService.update(id, eventRequest, header));
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.status, e.getStatus());
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.message, e.getMessage());

        verify(eventRepository).findByIdAndIsDeleted(id, false);
        verify(eventRepository).findBySlug(slug);
    }

    @Test
    void testUpdate_CoverUpdated() {
        event.setCover("different-cover.png");
        when(eventRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(event));
        when(eventRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(fileService.saveFileFromBase64(any())).thenReturn("save-file.png");
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.update(id, eventRequest, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());

        verify(eventRepository).findByIdAndIsDeleted(id, false);
        verify(eventRepository).findBySlug(slug);
        verify(fileService).saveFileFromBase64(any());
        verify(eventRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.delete(id, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());
        assertTrue(response.isDeleted());

        verify(eventRepository).findById(id);
        verify(eventRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        event.setDeleted(true);
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        EventResponse response = eventService.delete(id, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());
        assertTrue(response.isDeleted());

        verify(eventRepository).findById(id);
        verify(eventRepository).delete(any());
        verify(fileService).deleteFile(any());
    }

    @Test
    void testRestore_Success() {
        when(eventRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenReturn(event);

        EventResponse response = eventService.restore(id, header);
        assertEquals(event.getId(), response.getId());
        assertEquals(event.getTitle(), response.getTitle());
        assertFalse(response.isDeleted());

        verify(eventRepository).findByIdAndIsDeleted(id, true);
        verify(eventRepository).save(any());
    }

    @Test
    void testCountAll() {
        when(eventRepository.count()).thenReturn(10L);
        assertEquals(10L, eventService.countAll());
        verify(eventRepository).count();
    }

    @Test
    void testCountPerMonthByYear() {
        List<Map<String, Object>> maps = List.of(Map.of("event_month", 1, "total_event", 10L));
        when(eventRepository.countPerMonthByYear(2024)).thenReturn(maps);

        List<EventPerMonthResponse> responses = eventService.countPerMonthByYear(2024);
        assertEquals(12, responses.size());

        verify(eventRepository).countPerMonthByYear(2024);
    }
}