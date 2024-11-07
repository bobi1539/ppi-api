package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.TNewsletterEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterEmailRepository extends JpaRepository<TNewsletterEmail, Long> {

    Optional<TNewsletterEmail> findByEmail(String email);
}
