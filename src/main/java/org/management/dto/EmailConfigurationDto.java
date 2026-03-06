package org.management.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmailConfigurationDto {
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

}
