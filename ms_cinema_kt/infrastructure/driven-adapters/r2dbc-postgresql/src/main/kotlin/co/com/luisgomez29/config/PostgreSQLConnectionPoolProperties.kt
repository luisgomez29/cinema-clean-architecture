package co.com.luisgomez29.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "adapters.postgres.pool")
data class PostgreSQLConnectionPoolProperties(
    val initialSize: Int,
    val maxSize: Int,
    val maxIdleTime: Int
)