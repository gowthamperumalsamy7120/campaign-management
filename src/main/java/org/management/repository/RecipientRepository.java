package org.management.repository;

import org.management.entity.Recipient;
import org.management.interfaces.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Integer> {
    Optional<Recipient> findByEmail(String email);
    List<Recipient> findBySubscriptionStatus(SubscriptionStatus status);
    boolean existsByEmail(String email);
}