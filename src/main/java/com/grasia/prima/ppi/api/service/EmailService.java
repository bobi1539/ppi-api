package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.SendEmailRequest;

public interface EmailService {

    void sendEmailHtmlContent(SendEmailRequest request);
}
