package org.management.repository;

import org.management.entity.DeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryLogRepository extends JpaRepository<DeliveryLog, Integer> {
    List<DeliveryLog> findByCampaignId(Integer campaignId);
    long countByCampaignId(Integer campaignId);
    long countByCampaignIdAndStatus(Integer campaignId, String status);
}