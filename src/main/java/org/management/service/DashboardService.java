package org.management.service;

import org.management.dto.DashboardDto;
import org.management.entity.Campaign;
import org.management.repository.CampaignRepository;
import org.management.repository.DeliveryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private DeliveryLogRepository deliveryLogRepository;

    public DashboardDto getDashboard(Integer campaignId) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        long total = deliveryLogRepository.countByCampaignId(campaignId);
        long sent = deliveryLogRepository.countByCampaignIdAndStatus(campaignId, "SENT");
        long failed = deliveryLogRepository.countByCampaignIdAndStatus(campaignId, "FAILED");

        DashboardDto dashboard = new DashboardDto();
        dashboard.setCampaignId(campaign.getId());
        dashboard.setCampaignName(campaign.getCampaignName());
        dashboard.setCampaignStatus(campaign.getStatus().toString());
        dashboard.setTotalRecipients(total);
        dashboard.setSentCount(sent);
        dashboard.setFailedCount(failed);
        return dashboard;
    }
}
