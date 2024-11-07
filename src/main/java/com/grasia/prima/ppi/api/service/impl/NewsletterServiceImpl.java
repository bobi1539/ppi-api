package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.constant.Endpoint;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.Base64ToFileDto;
import com.grasia.prima.ppi.api.dto.request.*;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.dto.response.NewsletterSubscriptionResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MNewsletter;
import com.grasia.prima.ppi.api.entity.TNewsletterSubscription;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.SpecificationHelper;
import com.grasia.prima.ppi.api.helper.StringHelper;
import com.grasia.prima.ppi.api.repository.NewsletterRepository;
import com.grasia.prima.ppi.api.repository.NewsletterSubscriptionRepository;
import com.grasia.prima.ppi.api.service.AbstractCrudService;
import com.grasia.prima.ppi.api.service.EmailService;
import com.grasia.prima.ppi.api.service.FileService;
import com.grasia.prima.ppi.api.service.NewsletterService;
import jakarta.annotation.PreDestroy;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@AllArgsConstructor
@Service
@Slf4j
public class NewsletterServiceImpl extends AbstractCrudService implements NewsletterService {

    private final NewsletterRepository newsletterRepository;
    private final FileService fileService;
    private final NewsletterSubscriptionRepository newsletterSubscriptionRepository;
    private final AppConfig appConfig;
    private final EmailService emailService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static final String DIRECTORY_NAME = "newsletter";

    @Override
    public List<NewsletterResponse> findAll(SearchDto searchDto) {
        List<MNewsletter> newsletters = newsletterRepository.findAll(getSpecificationFindAll(searchDto), sortByIdDesc());
        return newsletters.stream().map(this::toResponse).toList();
    }

    @Override
    public Page<NewsletterResponse> findAllPagination(SearchDto searchDto) {
        Page<MNewsletter> newsletters = newsletterRepository
                .findAll(getSpecificationFindAll(searchDto), pageableSortByIdDesc(searchDto));
        return newsletters.map(this::toResponse);
    }

    @Override
    public NewsletterResponse findById(Long id) {
        return toResponse(getNewsletterById(id));
    }

    @Override
    public NewsletterResponse findBySlug(String slug) {
        MNewsletter newsletter = newsletterRepository.findBySlug(slug).orElseThrow(getNotFoundException());
        return toResponse(newsletter);
    }

    @Transactional
    @Override
    public NewsletterResponse create(NewsletterRequest request, HeaderRequest header) {
        MNewsletter newsletter = MNewsletter.builder().build();
        newsletter.setSlug(getSlugWhenCreate(request.getTitle()));
        newsletter.setTitle(request.getTitle());
        newsletter.setDescription(request.getDescription());
        newsletter.setCover(saveFile(request.getCover().getFileName(), request.getCover().getFileBase64()));
        newsletter.setContent(saveFile(request.getContent().getFileName(), request.getContent().getFileBase64()));
        setCreatedBy(newsletter, header);
        setUpdatedBy(newsletter, header);
        executorService.submit(() -> sendEmail(newsletter));

        return toResponse(newsletterRepository.save(newsletter));
    }

    @Transactional
    @Override
    public NewsletterResponse update(Long id, NewsletterRequest request, HeaderRequest header) {
        MNewsletter newsletter = getNewsletterById(id);
        newsletter.setSlug(getSlugWhenUpdate(request.getTitle(), newsletter));
        newsletter.setTitle(request.getTitle());
        newsletter.setDescription(request.getDescription());
        saveAndDeleteCover(newsletter, request);
        saveAndDeleteContent(newsletter, request);
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

    @Override
    public long countAll() {
        return newsletterRepository.count();
    }

    @Override
    public NewsletterSubscriptionResponse subscribe(NewsletterSubscriptionRequest request) {
        validateNewsletterSubscriptionRequest(request);

        TNewsletterSubscription subscription = TNewsletterSubscription.builder()
                .email(request.getEmail())
                .build();
        return toResponse(newsletterSubscriptionRepository.save(subscription));
    }

    @Override
    public void sendEmail(MNewsletter newsletter) {
        List<TNewsletterSubscription> subscriptions = newsletterSubscriptionRepository.findAll();
        for (TNewsletterSubscription subscription : subscriptions) {
            String feHost = String.format("%s:%s/newsletter", appConfig.getFeHost(), appConfig.getFePort());
            String body = getBody(newsletter, feHost);
            log.info("body : {}", body);

            String subject = "PPI Warwick - Newsletter";
            SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                    .to(subscription.getEmail())
                    .subject(subject)
                    .body(body)
                    .build();
            emailService.sendEmailHtmlContent(sendEmailRequest);
        }
    }

    private String getBody(MNewsletter newsletter, String feHost) {
        String cover = String.format(
                "%s:%s%s/download?directoryName=%s&fileName=%s",
                appConfig.getBeHost(),
                appConfig.getBePort(),
                Endpoint.FILE,
                DIRECTORY_NAME,
                newsletter.getCover()
        );

        return String.format("""
                        <div>
                            <a href="%s">
                              <img src="%s" alt="%s">
                              <h3>%s</h3>
                            </a>
                        </div>
                          """,
                feHost,
                cover,
                newsletter.getTitle(),
                newsletter.getTitle()
        );
    }

    @PreDestroy
    public void shutdownExecutorService() {
        log.info("Shutting down ExecutorService...");
        executorService.shutdown();
    }

    private Specification<MNewsletter> getSpecificationFindAll(SearchDto searchDto) {
        Specification<MNewsletter> spec = SpecificationHelper.stringLike(MNewsletter.FIELD_TITLE, searchDto.getSearch());
        return spec.and(getSpecificationIsDeleted(searchDto.getIsDeleted()));
    }

    private String getSlugWhenCreate(String title) {
        String slug = StringHelper.createSlug(title);
        Optional<MNewsletter> newsletterOptional = newsletterRepository.findBySlug(slug);
        if (newsletterOptional.isPresent()) {
            throw new BusinessException(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST);
        }
        return slug;
    }

    private String getSlugWhenUpdate(String title, MNewsletter newsletter) {
        String slug = StringHelper.createSlug(title);
        Optional<MNewsletter> newsletterOptional = newsletterRepository.findBySlug(slug);
        if (newsletterOptional.isPresent() && !newsletterOptional.get().getId().equals(newsletter.getId())) {
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

    private void saveAndDeleteCover(MNewsletter newsletter, NewsletterRequest request) {
        if (!newsletter.getCover().equals(request.getCover().getFileName())) {
            deleteFile(newsletter.getCover());
            newsletter.setCover(saveFile(request.getCover().getFileName(), request.getCover().getFileBase64()));
        }
    }

    private void saveAndDeleteContent(MNewsletter newsletter, NewsletterRequest request) {
        if (!newsletter.getContent().equals(request.getContent().getFileName())) {
            deleteFile(newsletter.getContent());
            newsletter.setContent(saveFile(request.getContent().getFileName(), request.getContent().getFileBase64()));
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
        return NewsletterResponse.toResponse(newsletter);
    }

    private void validateNewsletterSubscriptionRequest(NewsletterSubscriptionRequest request) {
        Optional<TNewsletterSubscription> email = newsletterSubscriptionRepository.findByEmail(request.getEmail());
        if (email.isPresent()) {
            throw new BusinessException(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE);
        }
    }

    private NewsletterSubscriptionResponse toResponse(TNewsletterSubscription newsletterEmail) {
        return NewsletterSubscriptionResponse.toResponse(newsletterEmail);
    }
}
