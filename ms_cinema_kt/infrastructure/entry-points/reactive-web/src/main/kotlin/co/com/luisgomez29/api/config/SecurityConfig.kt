package co.com.luisgomez29.api.config


import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import java.time.Duration

@Configuration
@EnableWebFluxSecurity
open class SecurityConfig {

    private val csp = "default-src 'none'; img-src 'self'; script-src 'self'; style-src 'self'; " +
            "object-src 'none'; frame-ancestors 'none'; upgrade-insecure-requests"

    @Bean
    open fun webFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            csrf {
                disable()
            }
            headers {
                hsts {
                    includeSubdomains = true
                    preload = true
                    maxAge = Duration.ofDays(365)
                }
                contentSecurityPolicy {
                    policyDirectives = csp
                }
                xssProtection {
                    disable()
                }
                frameOptions {
                    disable()
                }
                referrerPolicy {
                    policy = ReferrerPolicy.SAME_ORIGIN
                }
            }
        }
    }

    @Bean
    open fun corsWebFilter(@Value("\${cors.allowed-origins}") origins: List<String>): CorsWebFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = origins
        config.allowedHeaders = listOf(CorsConfiguration.ALL)
        config.setAllowedMethods(listOf("POST", "GET", "DELETE", "PUT"))

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }
}
