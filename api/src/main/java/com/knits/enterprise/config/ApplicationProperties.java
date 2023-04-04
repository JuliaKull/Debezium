package com.knits.enterprise.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Product.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "edm", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

    private String secret;
    private String issuer;
    private long  jwtExpirationMillis;
    private String monitoringEmail;
    private Boolean initData;

}
