package io.github.mmpodkanski;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String jwtSecret;
    private String jwtExpirationMs;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public String getJwtExpirationMs() {
        return jwtExpirationMs;
    }
}
