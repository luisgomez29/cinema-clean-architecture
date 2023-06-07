package co.com.luisgomez29.secretsmanager;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "adapters.secrets-manager")
public record SecretsNameStandard(String postgres) {
}
