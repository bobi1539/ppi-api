package com.grasia.prima.ppi.api.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DashboardResponse {
    private long totalStudent;
    private long totalEvent;
    private long totalNewsletter;
    private long totalGallery;
    private List<StudentEducationResponse> studentEducations;
}
