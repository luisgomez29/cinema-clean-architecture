package co.com.luisgomez29.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "adapters.postgres")
public record PostgresqlConnectionProperties(
        String database,
        String schema,
        String username,
        String password,
        String host,
        Integer port) {
}
