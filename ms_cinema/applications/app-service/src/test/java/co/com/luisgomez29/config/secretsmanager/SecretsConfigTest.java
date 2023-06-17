package co.com.luisgomez29.config.secretsmanager;


import co.com.bancolombia.secretsmanager.api.GenericManager;
import co.com.bancolombia.secretsmanager.api.exceptions.SecretException;
import co.com.luisgomez29.r2dbc.config.PostgresqlConnectionProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SecretsConfigTest {

    @InjectMocks
    private SecretsConfig secretsConfig;

    @Mock
    private SecretsManagerProperties properties;

    @Mock
    private GenericManager connector;

    private PostgresqlConnectionProperties postgresqlConnectionProperties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(properties.endpoint()).thenReturn("http://localhost:4566");
        when(properties.cacheSize()).thenReturn(5);
        when(properties.cacheTime()).thenReturn(3600);
        when(properties.postgres()).thenReturn("secretPostgres");

        postgresqlConnectionProperties = new PostgresqlConnectionProperties(
                "host",
                5432,
                "db",
                "schema",
                "username",
                "password"
        );
    }

    @Test
    void getConnectionAwsIsNotNull() {
        assertThat(secretsConfig.connectionAws()).isNotNull();
    }

    @Test
    void getConnectionLocalIsNotNull() {
        assertThat(secretsConfig.connectionLocal()).isNotNull();
    }

    @Test
    void getSecretSuccess() throws SecretException {
        when(connector.getSecret(anyString(), any()))
                .thenReturn(postgresqlConnectionProperties);

        PostgresqlConnectionProperties secret = secretsConfig.getSecret(
                properties.postgres(), PostgresqlConnectionProperties.class, connector
        );

        assertAll(
                "getSecretSuccess",
                () -> assertThat(secret).isEqualTo(postgresqlConnectionProperties),
                () -> assertThat(secretsConfig.getSecretPostgres(connector)).isEqualTo(postgresqlConnectionProperties)
        );
    }

    @Test
    void getSecretWithException() throws SecretException {
        when(connector.getSecret(anyString(), any()))
                .thenThrow(new SecretException("Exception"));
        assertThat(secretsConfig.getSecret(properties.postgres(), PostgresqlConnectionProperties.class, connector))
                .isNull();
    }
}
