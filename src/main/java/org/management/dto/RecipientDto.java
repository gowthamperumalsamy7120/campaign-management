package org.management.dto;

import lombok.*;
import org.management.interfaces.SubscriptionStatus;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipientDto {
    private String name;
    private String email;
    private SubscriptionStatus subscriptionStatus;

}
