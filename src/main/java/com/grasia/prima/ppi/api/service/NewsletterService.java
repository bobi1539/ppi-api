package com.grasia.prima.ppi.api.service;

import com.grasia.prima.ppi.api.dto.request.HeaderRequest;
import com.grasia.prima.ppi.api.dto.request.NewsletterRequest;
import com.grasia.prima.ppi.api.dto.response.NewsletterResponse;
import com.grasia.prima.ppi.api.dto.search.SearchDto;
import com.grasia.prima.ppi.api.entity.MNewsletter;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsletterService {

    List<NewsletterResponse> findAll(SearchDto searchDto);

    Page<NewsletterResponse> findAllPagination(SearchDto searchDto);

    NewsletterResponse findById(Long id);

    NewsletterResponse create(NewsletterRequest request, HeaderRequest header);

    NewsletterResponse update(Long id, NewsletterRequest request, HeaderRequest header);

    NewsletterResponse delete(Long id, HeaderRequest header);

    NewsletterResponse restore(Long id, HeaderRequest header);

    MNewsletter getNewsletterById(Long id);
}
