package co.com.luisgomez29.r2dbc.config;

import co.com.luisgomez29.secretsmanager.SecretsManager;
import co.com.luisgomez29.secretsmanager.SecretsNameStandard;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class PostgreSQLConnectionPool {
    // TODO: change pool connection properties based on your resources.
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;

    private final SecretsManager secretsManager;
    private final SecretsNameStandard secretsNameStandard;

    private PostgresqlConnectionProperties postgresProperties() {
        return secretsManager.getSecret(secretsNameStandard.postgres(), PostgresqlConnectionProperties.class).block();
    }

    @Bean
    public ConnectionPool getConnectionConfig() {
        return buildConnectionConfiguration(postgresProperties());
    }

    private ConnectionPool buildConnectionConfiguration(PostgresqlConnectionProperties properties) {
        PostgresqlConnectionConfiguration dbConfiguration = PostgresqlConnectionConfiguration.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDbname())
                .schema(properties.getSchema())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .build();

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(new PostgresqlConnectionFactory(dbConfiguration))
                .name("api-postgres-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

        return new ConnectionPool(poolConfiguration);
    }
}
