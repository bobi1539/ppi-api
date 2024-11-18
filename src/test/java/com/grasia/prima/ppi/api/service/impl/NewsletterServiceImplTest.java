package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.config.AppConfig;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.request.NewsletterRequest;
import com.grasia.prima.ppi.api.dto.request.NewsletterSubscriptionRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.dto.response.NewsletterSubscriptionResponse;
import com.grasia.prima.ppi.api.entity.MNewsletter;
import com.grasia.prima.ppi.api.entity.TNewsletterSubscription;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import com.grasia.prima.ppi.api.repository.NewsletterRepository;
import com.grasia.prima.ppi.api.repository.NewsletterSubscriptionRepository;
import com.grasia.prima.ppi.api.service.EmailService;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewsletterServiceImplTest extends ServiceTest {

    @InjectMocks
    private NewsletterServiceImpl newsletterService;

    @Mock
    private NewsletterRepository newsletterRepository;

    @Mock
    private FileService fileService;

    @Mock
    private NewsletterSubscriptionRepository subscriptionRepository;

    @Mock
    private AppConfig appConfig;

    @Mock
    private EmailService emailService;

    private final MNewsletter newsletter = ObjectDummy.getNewsletter();
    private final NewsletterRequest newsletterRequest = ObjectDummy.getNewsletterRequest();
    private final NewsletterSubscriptionRequest subscriptionRequest = ObjectDummy.getNewsletterSubscriptionRequest();
    private final TNewsletterSubscription subscription = ObjectDummy.getNewsletterSubscription();
    private final String email = subscription.getEmail();
    private final String slug = "test";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAll_Success() {
        when(newsletterRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(getNewsletters());

        List<NewsletterResponse> responses = newsletterService.findAll(searchDto);
        assertEquals(2, responses.size());

        verify(newsletterRepository).findAll(any(Specification.class), any(Sort.class));
    }

    private List<MNewsletter> getNewsletters() {
        return List.of(newsletter, newsletter);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFindAllPagination_Success() {
        when(newsletterRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(getNewsletterPage());

        Page<NewsletterResponse> responses = newsletterService.findAllPagination(searchDto);
        assertEquals(2, responses.getTotalElements());

        verify(newsletterRepository).findAll(any(Specification.class), any(Pageable.class));
    }

    private Page<MNewsletter> getNewsletterPage() {
        return new PageImpl<>(getNewsletters());
    }

    @Test
    void testFindById_Success() {
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));

        NewsletterResponse response = newsletterService.findById(id);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testFindBySlug_Success() {
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.of(newsletter));

        NewsletterResponse response = newsletterService.findBySlug(slug);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findBySlug(slug);
    }

    @Test
    void testCreate_NewsletterBySlugIsEmpty() {
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.create(newsletterRequest, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findBySlug(slug);
        verify(newsletterRepository).save(any());
    }

    @Test
    void testCreate_NewsletterBySlugIsPresent() {
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.of(newsletter));

        BusinessException e = assertThrows(BusinessException.class, () -> newsletterService.create(newsletterRequest, header));
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.status, e.getStatus());
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.message, e.getMessage());

        verify(newsletterRepository).findBySlug(slug);
    }

    @Test
    void testUpdate_NewsletterBySlugIsEmpty() {
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.update(id, newsletterRequest, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
        verify(newsletterRepository).findBySlug(slug);
        verify(newsletterRepository).save(any());
    }

    @Test
    void testUpdate_NewsletterBySlugIsPresentAndSlugIsEqualsWithExisting() {
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.update(id, newsletterRequest, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
        verify(newsletterRepository).findBySlug(slug);
        verify(newsletterRepository).save(any());
    }

    @Test
    void testUpdate_NewsletterBySlugIsPresentAndSlugIsDifferentWithExisting() {
        MNewsletter different = ObjectDummy.getNewsletter();
        different.setId(100L);
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.of(different));
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));

        BusinessException e = assertThrows(BusinessException.class, () -> newsletterService.update(id, newsletterRequest, header));
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.status, e.getStatus());
        assertEquals(GlobalMessage.SLUG_FROM_TITLE_ALREADY_EXIST.message, e.getMessage());

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
        verify(newsletterRepository).findBySlug(slug);
    }

    @Test
    void testUpdate_CoverAndContentUpdated() {
        newsletter.setCover("different-cover.png");
        newsletter.setContent("different-content.png");
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.findBySlug(slug)).thenReturn(Optional.empty());
        when(fileService.saveFileFromBase64(any())).thenReturn("save-file.png");
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.update(id, newsletterRequest, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
        verify(newsletterRepository).findBySlug(slug);
        verify(fileService, times(2)).saveFileFromBase64(any());
        verify(newsletterRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedFalse() {
        when(newsletterRepository.findById(id)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.delete(id, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());
        assertTrue(response.isDeleted());

        verify(newsletterRepository).findById(id);
        verify(newsletterRepository).save(any());
    }

    @Test
    void testDelete_IsDeletedTrue() {
        newsletter.setDeleted(true);
        when(newsletterRepository.findById(id)).thenReturn(Optional.of(newsletter));

        NewsletterResponse response = newsletterService.delete(id, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());
        assertTrue(response.isDeleted());

        verify(newsletterRepository).findById(id);
        verify(newsletterRepository).delete(any());
    }

    @Test
    void testRestore_Success() {
        when(newsletterRepository.findByIdAndIsDeleted(id, true)).thenReturn(Optional.of(newsletter));
        when(newsletterRepository.save(any())).thenReturn(newsletter);

        NewsletterResponse response = newsletterService.restore(id, header);
        assertEquals(newsletter.getId(), response.getId());
        assertEquals(newsletter.getTitle(), response.getTitle());
        assertFalse(response.isDeleted());

        verify(newsletterRepository).findByIdAndIsDeleted(id, true);
        verify(newsletterRepository).save(any());
    }

    @Test
    void testCountAll() {
        when(newsletterRepository.count()).thenReturn(10L);
        assertEquals(10L, newsletterService.countAll());
        verify(newsletterRepository).count();
    }

    @Test
    void testSubscribe_Success() {
        when(subscriptionRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(subscriptionRepository.save(any())).thenReturn(subscription);

        NewsletterSubscriptionResponse response = newsletterService.subscribe(subscriptionRequest);
        assertEquals(subscription.getId(), response.getId());
        assertEquals(subscription.getEmail(), response.getEmail());

        verify(subscriptionRepository).findByEmail(email);
        verify(subscriptionRepository).save(any());
    }

    @Test
    void testSubscribe_Failed() {
        when(subscriptionRepository.findByEmail(email)).thenReturn(Optional.of(subscription));

        BusinessException e = assertThrows(BusinessException.class, () -> newsletterService.subscribe(subscriptionRequest));

        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE.status, e.getStatus());
        assertEquals(GlobalMessage.EMAIL_HAS_BEEN_SUBSCRIBE.message, e.getMessage());

        verify(subscriptionRepository).findByEmail(email);
    }

    @Test
    void testSendEmail() {
        when(appConfig.getBeHost()).thenReturn("http://localhost");
        when(appConfig.getBePort()).thenReturn("8080");
        when(appConfig.getFeHost()).thenReturn("http://localhost");
        when(appConfig.getFePort()).thenReturn("3000");
        when(subscriptionRepository.findAll()).thenReturn(List.of(subscription));

        assertDoesNotThrow(() -> newsletterService.sendEmail(newsletter));

        verify(subscriptionRepository).findAll();
        verify(emailService).sendEmailHtmlContent(any());
    }

    @Test
    void testResendEmail() {
        when(newsletterRepository.findByIdAndIsDeleted(id, false)).thenReturn(Optional.of(newsletter));

        assertDoesNotThrow(() -> newsletterService.resendEmail(id));

        verify(newsletterRepository).findByIdAndIsDeleted(id, false);
    }

    @Test
    void testShutdownExecutorService() {
        assertDoesNotThrow(() -> newsletterService.shutdownExecutorService());
    }
}