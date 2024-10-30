package com.grasia.prima.ppi.api.dto.response;

import com.grasia.prima.ppi.api.entity.MNewsletter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

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

    public static NewsletterResponse toResponse(MNewsletter newsletter) {
        if (Objects.isNull(newsletter)) {
            return null;
        }
        NewsletterResponse response = builder()
                .id(newsletter.getId())
                .title(newsletter.getTitle())
                .slug(newsletter.getSlug())
                .description(newsletter.getDescription())
                .cover(newsletter.getCover())
                .content(newsletter.getContent())
                .build();
        BaseEntityResponse.setBaseEntity(response, newsletter);
        return response;
    }
}
