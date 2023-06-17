package co.com.luisgomez29.config.secretsmanager;

import co.com.bancolombia.secretsmanager.api.GenericManager;
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnector;
import co.com.luisgomez29.r2dbc.config.PostgresqlConnectionProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.regions.Region;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SecretsConfig {

    private final SecretsManagerProperties properties;
    private static final String REGION_SECRET = Region.US_EAST_1.toString();

    @Bean
    @Profile({"dev", "qa", "pdn"})
    public GenericManager connectionAws() {
        return new AWSSecretManagerConnector(REGION_SECRET);
    }

    @Bean
    @Profile("local")
    public GenericManager connectionLocal() {
        return new AWSSecretManagerConnector(REGION_SECRET, properties.endpoint());
    }

    public <T> T getSecret(String secretName, Class<T> cls, GenericManager connector) {
        try {
            log.info("Secret was obtained successfully");
            return connector.getSecret(secretName, cls);
        } catch (Exception e) {
            log.error(String.format("Error getting secret: %s", e.getMessage()));
            return null;
        }
    }

    @Bean
    public PostgresqlConnectionProperties getSecretPostgres(GenericManager connector) {
        return this.getSecret(properties.postgres(), PostgresqlConnectionProperties.class, connector);

    }

}
