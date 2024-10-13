package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.FileRequest;
import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.NewsletterRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MNewsletter;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.entity.NewsletterHelper;
import com.grasia.prima.ppi.api.repository.NewsletterRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.NewsletterService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class NewsletterServiceImpl extends AbstractCrudService implements NewsletterService {

    private final NewsletterRepository newsletterRepository;
    private final FileService fileService;
    private static final String DIRECTORY_NAME = "newsletter";

    @Override
    public List<NewsletterResponse> findAll(SearchDto searchDto) {
        List<MNewsletter> newsletters = newsletterRepository.findAll(getSpecificationFindAll(searchDto), sortByIdAsc());
        return newsletters.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<NewsletterResponse> findAllPagination(SearchDto searchDto) {
        Page<MNewsletter> newsletters = newsletterRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdAsc(searchDto));
        return newsletters.map(this::toResponse);
    }

    @Override
    public NewsletterResponse findById(Long id) {
        return toResponse(getNewsletterById(id));
    }

    @Transactional
    @Override
    public NewsletterResponse create(NewsletterRequest request, HeaderRequest header) {
        MNewsletter newsletter = MNewsletter.builder().build();
        setNewsletter(newsletter, request);
        setCreatedBy(newsletter, header);
        setUpdatedBy(newsletter, header);

        return toResponse(newsletterRepository.save(newsletter));
    }

    @Transactional
    @Override
    public NewsletterResponse update(Long id, NewsletterRequest request, HeaderRequest header) {
        MNewsletter newsletter = getNewsletterById(id);
        setNewsletter(newsletter, request);
        setUpdatedBy(newsletter, header);

        return toResponse(newsletterRepository.save(newsletter));
    }

    @Transactional
    @Override
    public NewsletterResponse delete(Long id, HeaderRequest header) {
        MNewsletter newsletter = newsletterRepository.findById(id).orElseThrow(getNotFoundException());
        if (newsletter.isDeleted()) {
            newsletterRepository.delete(newsletter);
            deleteFile(newsletter.getCover());
            deleteFile(newsletter.getContent());
        } else {
            newsletter.setDeleted(true);
            setUpdatedBy(newsletter, header);
            newsletter = newsletterRepository.save(newsletter);
        }
        return toResponse(newsletter);
    }

    @Transactional
    @Override
    public NewsletterResponse restore(Long id, HeaderRequest header) {
        MNewsletter newsletter = getNewsletterDeleted(id);
        newsletter.setDeleted(false);
        setUpdatedBy(newsletter, header);

        return toResponse(newsletterRepository.save(newsletter));
    }

    @Override
    public MNewsletter getNewsletterById(Long id) {
        return newsletterRepository.findByIdAndIsDeleted(id, false).orElseThrow(getNotFoundException());
    }

    private Specification<MNewsletter> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MNewsletter> spec = SpecificationHelper.stringLike(MNewsletter.FIELD_TITLE, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private void setNewsletter(MNewsletter newsletter, NewsletterRequest request) {
        newsletter.setTitle(request.getTitle());
        newsletter.setDescription(request.getDescription());

        if (Objects.isNull(newsletter.getCover())) {
            newsletter.setCover(saveFile(request.getCoverFileName(), request.getCoverBase64()));
        } else {
            saveAndDeleteCover(newsletter, request);
        }

        if (Objects.isNull(newsletter.getContent())) {
            newsletter.setContent(saveFile(request.getContentFileName(), request.getContentBase64()));
        } else {
            saveAndDeleteContent(newsletter, request);
        }
    }

    private String saveFile(String fileName, String base64String) {
        Base64ToFileDto dto = Base64ToFileDto.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .base64String(base64String)
                .build();
        return fileService.saveFileFromBase64(dto);
    }

    private void saveAndDeleteCover(MNewsletter newsletter, NewsletterRequest request) {
        if (!newsletter.getCover().equals(request.getCoverFileName())) {
            deleteFile(newsletter.getCover());
            newsletter.setCover(saveFile(request.getCoverFileName(), request.getCoverBase64()));
        }
    }

    private void saveAndDeleteContent(MNewsletter newsletter, NewsletterRequest request) {
        if (!newsletter.getContent().equals(request.getContentFileName())) {
            deleteFile(newsletter.getContent());
            newsletter.setContent(saveFile(request.getContentFileName(), request.getContentBase64()));
        }
    }

    private void deleteFile(String fileName) {
        FileRequest fileRequest = FileRequest.builder()
                .directoryName(DIRECTORY_NAME)
                .fileName(fileName)
                .build();
        fileService.deleteFile(fileRequest);
    }

    private MNewsletter getNewsletterDeleted(Long id) {
        return newsletterRepository.findByIdAndIsDeleted(id, true).orElseThrow(getNotFoundException());
    }

    private NewsletterResponse toResponse(MNewsletter newsletter) {
        return NewsletterHelper.toNewsletterResponse(newsletter);
    }
}
