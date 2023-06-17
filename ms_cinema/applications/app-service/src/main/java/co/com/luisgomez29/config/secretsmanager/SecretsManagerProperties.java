package co.com.luisgomez29.config.secretsmanager;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "helpers.secrets-manager")
public record SecretsManagerProperties(
        String endpoint,
        Integer cacheSize,
        Integer cacheTime,
        String postgres
) {
}