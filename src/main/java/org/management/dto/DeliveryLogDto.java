package org.management.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryLogDto {
    private String recipientEmail;
    private String status;
    private String failureReason;
}
