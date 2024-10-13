package com.grasia.prima.ppi.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class NewsletterResponse extends BaseEntityResponse {
    private Long id;
    private String title;
    private String slug;
    private String description;
    private String cover;
    private String content;
}
