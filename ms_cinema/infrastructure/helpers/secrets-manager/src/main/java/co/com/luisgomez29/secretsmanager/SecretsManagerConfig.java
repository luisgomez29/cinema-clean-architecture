package co.com.luisgomez29.secretsmanager;

import co.com.bancolombia.secretsmanager.config.AWSSecretsManagerConfig;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class SecretsManagerConfig {

    private final SecretsManagerProperties properties;

    @Bean
    @Profile({"dev", "qa", "pdn"})
    public AWSSecretManagerConnectorAsync connectionAws() {
        return new AWSSecretManagerConnectorAsync(AWSSecretsManagerConfig.builder()
                .cacheSize(properties.cacheSize())
                .cacheSeconds(properties.cacheTime())
                .build());
    }

    @Bean
    @Profile("local")
    public AWSSecretManagerConnectorAsync connectionLocal() {
        return new AWSSecretManagerConnectorAsync(AWSSecretsManagerConfig.builder()
                .endpoint(properties.endpoint())
                .build());
    }
}