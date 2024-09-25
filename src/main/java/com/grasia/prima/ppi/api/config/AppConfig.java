package com.grasia.prima.ppi.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app-config")
@Getter
@Setter
public class AppConfig {
    private String pathFile;
    private String jwtSecret;
    private String jwtExpiredDuration;
}
