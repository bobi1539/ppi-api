package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.TSecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecretKeyRepository extends JpaRepository<TSecretKey, Long> {

    Optional<TSecretKey> findByName(String name);

    Optional<TSecretKey> findByKey(String key);
}
