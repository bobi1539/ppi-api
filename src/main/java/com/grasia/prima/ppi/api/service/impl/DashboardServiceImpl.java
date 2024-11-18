package com.grasia.prima.ppi.api.service.impl;

import com.grasia.prima.ppi.api.dto.response.DashboardResponse;
import com.grasia.prima.ppi.api.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StudentService studentService;
    private final EventService eventService;
    private final NewsletterService newsletterService;
    private final GalleryService galleryService;

    @Override
    public DashboardResponse getDashboard(int year) {
        return DashboardResponse.builder()
                .totalStudent(studentService.countAll())
                .totalEvent(eventService.countAll())
                .totalNewsletter(newsletterService.countAll())
                .totalGallery(galleryService.countAll())
                .studentEducations(studentService.countByEducation())
                .eventPerMonths(eventService.countPerMonthByYear(year))
                .build();
    }
}
