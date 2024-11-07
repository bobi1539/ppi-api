package com.grasia.prima.ppi.api.repository;

import com.grasia.prima.ppi.api.entity.TNewsletterSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsletterSubscriptionRepository extends JpaRepository<TNewsletterSubscription, Long> {

    Optional<TNewsletterSubscription> findByEmail(String email);
}
