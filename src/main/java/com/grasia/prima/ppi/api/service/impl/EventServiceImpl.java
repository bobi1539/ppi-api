package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.EventRequest;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.response.EventResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MEvent;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.helper.entity.EventHelper;
import com.grasia.prima.ppi.api.repository.EventRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.EventService;
import com.grasia.prima.ppi.api.service.FileService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventServiceImpl extends AbstractCrudService implements EventService {

    private final EventRepository eventRepository;
    private final FileService fileService;
    private static final String DIRECTORY_NAME = "event";

    @Override
    public List<EventResponse> findAll(SearchDto searchDto) {
        List<MEvent> events = eventRepository.findAll(getSpecificationFindAll(searchDto), sortByIdDesc());
        return events.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<EventResponse> findAllPagination(SearchDto searchDto) {
        Page<MEvent> events = eventRepository.findAll(getSpecificationFindAll(searchDto), pageableSortByIdDesc(searchDto));
        return events.map(this::toResponse);
    }

    @Override
    public EventResponse findById(Long id) {
        return toResponse(getEventById(id));
    }

    @Override
    public EventResponse findBySlug(String slug) {
        MEvent event = eventRepository.findBySlug(slug).orElseThrow(getNotFoundException());
        return toResponse(event);
    }

    @Transactional
    @Override
    public EventResponse create(EventRequest request, HeaderRequest header) {
        MEvent event = MEvent.builder().build();
        setEvent(event, request);
        setCreatedBy(event, header);
        setUpdatedBy(event, header);
        event.setSlug(getSlugWhenCreate(request.getTitle()));
        event.setCover(saveFile(request.getCoverFileName(), request.getCoverBase64()));

        return toResponse(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventResponse update(Long id, EventRequest request, HeaderRequest header) {
        MEvent event = MEvent.builder().build();
        event.setSlug(getSlugWhenUpdate(request.getTitle(), event));
        setEvent(event, request);
        setUpdatedBy(event, header);
        saveAndDeleteCover(event, request);

        return toResponse(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventResponse delete(Long id, HeaderRequest header) {
        MEvent event = eventRepository.findById(id).orElseThrow(getNotFoundException());
        if (event.isDeleted()) {
            eventRepository.delete(event);
            deleteFile(event.getCover());
        } else {
            event.setDeleted(true);
            setUpdatedBy(event, header);
            event = eventRepository.save(event);
        }
        return toResponse(event);
    }

    @Transactional
    @Override
    public EventResponse restore(Long id, HeaderRequest header) {
        MEvent event = getEventDeleted(id);
        event.setDeleted(false);
        setUpdatedBy(event, header);

        return toResponse(eventRepository.save(event));
    }

    @Override
    public MEvent getEventById(Long id) {
        return eventRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MEvent> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MEvent> spec = SpecificationHelper.stringLike(MEvent.FIELD_TITLE, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setEvent(MEvent event, EventRequest request) {
        validateEventRequest(request);

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
    }

    private void validateEventRequest(EventRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BusinessException(GlobalMessage.START_END_DATE_NOT_VALID);
        }
    }

    private String getSlugWhenCreate(String title) {
        String slug = StringHelper.createSlug(title);
        Optional<MEvent> newsletterOptional = eventRepository.findBySlug(slug);
        if (newsletterOptional.isPresent()) {
            throw new BusinessException(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST);
        }
        return slug;
    }

    private String getSlugWhenUpdate(String title, MEvent event) {
        String slug = StringHelper.createSlug(title);
        Optional<MEvent> eventOptional = eventRepository.findBySlug(slug);
        if (eventOptional.isPresent() && !eventOptional.get().getId().equals(event.getId())) {
            throw new BusinessException(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST);
        }
        return slug;
    }

    private String saveFile(String fileName, String base64String) {
        Base64ToFileDto dto = Base64ToFileDto.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .base64String(base64String)
                .build();
        return fileService.saveFileFromBase64(dto);
    }

    private void saveAndDeleteCover(MEvent event, EventRequest request) {
        if (!event.getCover().equals(request.getCoverFileName())) {
            deleteFile(event.getCover());
            event.setCover(saveFile(request.getCoverFileName(), request.getCoverBase64()));
        }
    }

    private void deleteFile(String fileName) {
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .build();
        fileService.deleteFile(fileRequest);
    }

    private MEvent getEventDeleted(Long id) {
        return eventRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private EventResponse toResponse(MEvent event) {
        return EventHelper.toEventResponse(event);
    }
}
