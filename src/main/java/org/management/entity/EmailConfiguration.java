package org.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity
@Table(name = "mail_config")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmailConfiguration {
    @Id
    private Integer emailId;
    private String smtpServer;
    private Integer smtpPort;
    @Column(unique = true)
    @NotBlank(message = "user name may not be blank")
    private String userName;
    private String accountPassword;
    private Boolean authentication;
    @Column(columnDefinition = "boolean default true")
    private Boolean enableTLS;
    @Column(columnDefinition = "boolean default true")
    private Boolean active;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdOn;

}
