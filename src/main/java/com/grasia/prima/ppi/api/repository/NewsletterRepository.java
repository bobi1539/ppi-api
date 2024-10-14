package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MNewsletter;

import java.util.Optional;

public interface NewsletterRepository extends BaseRepository<MNewsletter, Long> {

    Optional<MNewsletter> findBySlug(String slug);
}
