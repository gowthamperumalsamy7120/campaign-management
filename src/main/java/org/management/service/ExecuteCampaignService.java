package org.management.service;

import lombok.RequiredArgsConstructor;
import org.management.entity.Campaign;
import org.management.entity.DeliveryLog;
import org.management.entity.Recipient;
import org.management.interfaces.CampaignStatus;
import org.management.interfaces.SubscriptionStatus;
import org.management.repository.CampaignRepository;
import org.management.repository.DeliveryLogRepository;
import org.management.repository.RecipientRepository;
import org.management.utils.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecuteCampaignService {
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private RecipientRepository recipientRepository;
    @Autowired
    private EmailConfigurationService emailService;
    @Autowired
    private DeliveryLogRepository logRepository;

    public ResponseModel executeCampaign(Integer campaignId) {

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        campaign.setStatus(CampaignStatus.IN_PROGRESS);
        campaignRepository.save(campaign);

        List<Recipient> recipients =
                recipientRepository.findBySubscriptionStatus(SubscriptionStatus.SUBSCRIBED);

        for (Recipient r : recipients) {

            DeliveryLog log = new DeliveryLog();
            log.setCampaignId(campaign.getId());
            log.setRecipientEmail(r.getEmail());
            try {
                emailService.sendEmail(
                        campaign.getSubjectLine(),
                        campaign.getEmailContent(),
                        r.getEmail());

                log.setStatus("SENT");
            } catch (Exception e) {
                log.setStatus("FAILED");
                log.setFailureReason(e.getMessage());
            }
            logRepository.save(log);
        }
        campaign.setStatus(CampaignStatus.COMPLETED);
        campaignRepository.save(campaign);
        return new ResponseModel(true, "Campaign executed successfully");
    }
}