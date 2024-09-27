package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.MUser;

import java.util.Optional;

public interface UserRepository extends BaseRepository<MUser, Long> {

    Optional<MUser> findByUsername(String username);

    Optional<MUser> findByEmail(String email);
}
