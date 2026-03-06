package org.management.entity;

import jakarta.persistence.*;
import lombok.*;
import org.management.interfaces.SubscriptionStatus;

@Entity
@Table(name="recipients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus subscriptionStatus;
}