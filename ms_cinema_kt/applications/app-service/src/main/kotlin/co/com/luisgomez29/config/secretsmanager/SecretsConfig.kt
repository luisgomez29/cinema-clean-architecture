package co.com.luisgomez29.config.secretsmanager

import co.com.bancolombia.secretsmanager.api.GenericManager
import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnector
import co.com.luisgomez29.config.PostgresqlConnectionProperties
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.regions.Region

@Configuration
open class SecretsConfig(private val properties: SecretsManagerProperties) {

    companion object {
        private val log = LogManager.getLogger(this::class.java)
        private val REGION = Region.US_EAST_1.toString()
    }

    @Bean
    @Profile("dev", "qa", "pdn")
    open fun connectionAws(): GenericManager {
        return AWSSecretManagerConnector(REGION)
    }

    @Bean
    @Profile("local")
    open fun connectionLocal(): GenericManager {
        return AWSSecretManagerConnector(REGION, properties.endpoint)
    }

    @Bean
    open fun getSecretPostgres(connector: GenericManager): PostgresqlConnectionProperties? {
        return getSecret(properties.postgres, PostgresqlConnectionProperties::class.java, connector)
    }

    private fun <T : Any> getSecret(secretName: String, cls: Class<T>, connector: GenericManager): T? {
        return try {
            log.info("Secret was obtained successfully")
            connector.getSecret(secretName, cls)
        } catch (e: Exception) {
            log.error("Error getting secret: {}", e.message)
            null
        }
    }
}
