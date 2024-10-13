package com.grasia.prima.ppi.api.helper.entity;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.entity.MNewsletter;
import com.grasia.prima.ppi.api.exception.BusinessException;

import java.util.Objects;

public final class NewsletterHelper {

    private NewsletterHelper() {
        throw new BusinessException(GlobalMessage.INTERNAL_SERVER_ERROR);
    }

    public static NewsletterResponse toNewsletterResponse(MNewsletter newsletter) {
        if (Objects.isNull(newsletter)) {
            return null;
        }
        NewsletterResponse response = NewsletterResponse.builder()
                .id(newsletter.getId())
                .title(newsletter.getTitle())
                .slug(newsletter.getSlug())
                .description(newsletter.getDescription())
                .cover(newsletter.getCover())
                .content(newsletter.getContent())
                .build();
        BaseEntityHelper.setBaseEntityResponse(response, newsletter);
        return response;
    }
}
