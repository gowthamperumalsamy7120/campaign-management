package org.management.dto;

import lombok.*;
import org.management.interfaces.CampaignStatus;

import java.time.Instant;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDto {
    private String campaignName;
    private String subjectLine;
    private String emailContent;
    private Instant scheduledTime;
    private CampaignStatus status;
}
