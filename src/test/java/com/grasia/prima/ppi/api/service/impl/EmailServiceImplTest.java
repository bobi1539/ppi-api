package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.request.SendEmailRequest;
import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.helper.ObjectDummy;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage message;

    private final SendEmailRequest sendEmailRequest = ObjectDummy.getSendEmailRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendEmailHtmlContent_Success() {
        when(mailSender.createMimeMessage()).thenReturn(message);
        assertDoesNotThrow(() -> emailService.sendEmailHtmlContent(sendEmailRequest));
        verify(mailSender).send((MimeMessage) any());
    }

    @Test
    void testSendEmailHtmlContent_Failed() {
        assertThrows(BusinessException.class, () -> emailService.sendEmailHtmlContent(sendEmailRequest));
    }
}