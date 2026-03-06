package org.management.dto;

import lombok.Data;
@Data
public class DashboardDto {
    private Integer campaignId;
    private String campaignName;
    private String campaignStatus;
    private long totalRecipients;
    private long sentCount;
    private long failedCount;
}
