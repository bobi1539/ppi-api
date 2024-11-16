package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.LogAuth;
import com.grasia.prima.ppi.api.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface LogAuthRepository extends JpaRepository<LogAuth, Long> {

    Optional<LogAuth> findByRefreshTokenAndRefreshTokenExpiryAfter(String refreshToken, LocalDate date);

    void deleteByUser(MUser user);
}
