package co.com.luisgomez29.config

import io.r2dbc.pool.ConnectionPool
import io.r2dbc.pool.ConnectionPoolConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
open class PostgreSQLConnectionPool(private val connectionPoolProperties: PostgreSQLConnectionPoolProperties) {

    @Bean
    open fun buildConnectionConfiguration(properties: PostgresqlConnectionProperties): ConnectionPool {
        val dbConfiguration = PostgresqlConnectionConfiguration.builder()
            .host(properties.host)
            .port(properties.port)
            .database(properties.dbname)
            .schema(properties.schema)
            .username(properties.username)
            .password(properties.password)
            .build()

        val poolConfiguration: ConnectionPoolConfiguration = ConnectionPoolConfiguration.builder()
            .connectionFactory(PostgresqlConnectionFactory(dbConfiguration))
            .name("api-postgres-connection-pool")
            .initialSize(connectionPoolProperties.initialSize)
            .maxSize(connectionPoolProperties.maxSize)
            .maxIdleTime(Duration.ofMinutes(connectionPoolProperties.maxIdleTime.toLong()))
            .build()
        return ConnectionPool(poolConfiguration)
    }
}
