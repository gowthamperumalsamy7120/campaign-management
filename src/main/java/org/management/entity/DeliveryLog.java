package org.management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="delivery_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String recipientEmail;
    private String status;
    private String failureReason;
    private Integer campaignId;
    @ManyToOne
    @JoinColumn(name = "campaignId",referencedColumnName = "id",insertable = false,updatable = false)
    private Campaign campaignDetails;

}
