package org.management.repository;

import org.management.entity.EmailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfigurationRepository extends JpaRepository<EmailConfiguration,Integer> {
    Optional<EmailConfiguration>findByActive(Boolean active);

}
