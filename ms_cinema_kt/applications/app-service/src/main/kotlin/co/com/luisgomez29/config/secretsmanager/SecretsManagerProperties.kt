package co.com.luisgomez29.config.secretsmanager

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "helpers.secrets-manager")
data class SecretsManagerProperties(
    val endpoint: String,
    val cacheSize: Int,
    val cacheTime: Int,
    val postgres: String
)