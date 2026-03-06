package org.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.management.interfaces.CampaignStatus;

import java.time.Instant;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String campaignName;
    private String subjectLine;
    @Column(columnDefinition = "TEXT")
    private String emailContent;
    private Instant scheduledTime;
    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

}