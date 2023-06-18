package co.com.luisgomez29.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "adapters.reactive-web")
public record ApiProperties(
        String basePath,
        String genre
) {
}
