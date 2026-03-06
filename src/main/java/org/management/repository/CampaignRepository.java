package org.management.repository;

import org.management.entity.Campaign;
import org.management.interfaces.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    List<Campaign> findByStatus(CampaignStatus status);
}
