package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MEvent;

import java.util.Optional;

public interface EventRepository extends BaseRepository<MEvent, Long> {

    Optional<MEvent> findBySlug(String slug);
}
