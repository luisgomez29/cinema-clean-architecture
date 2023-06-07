package co.com.luisgomez29.secretsmanager;

import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log
public class SecretsManager {

    private final AWSSecretManagerConnector awsSecretManagerConnector;

    public <T> T getSecret(String secretName, Class<T> cls) {
        try {
            log.info("Secret was obtained successfully");
            return awsSecretManagerConnector.getSecret(secretName, cls);
        } catch (Exception e) {
            log.severe(String.format("Error getting secret: %s", e.getMessage()));
            return null;
        }
    }
}
