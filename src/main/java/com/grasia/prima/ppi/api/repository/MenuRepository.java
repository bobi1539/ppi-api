package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MMenu;

import java.util.Optional;

public interface MenuRepository extends BaseRepository<MMenu, Long> {

    Optional<MMenu> findBySequence(Integer sequence);
}
