package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MMenu;
import com.grasia.prima.ppi.api.entity.MSubMenu;

import java.util.Optional;

public interface SubMenuRepository extends BaseRepository<MSubMenu, Long> {

    Optional<MSubMenu> findBySequenceAndMenu(Integer sequence, MMenu menu);
}
