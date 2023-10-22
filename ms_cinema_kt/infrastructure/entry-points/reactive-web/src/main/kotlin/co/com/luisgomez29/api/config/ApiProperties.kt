package co.com.luisgomez29.api.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "adapters.reactive-web")
data class ApiProperties(
    val basePath: String,
    val genre: String
)