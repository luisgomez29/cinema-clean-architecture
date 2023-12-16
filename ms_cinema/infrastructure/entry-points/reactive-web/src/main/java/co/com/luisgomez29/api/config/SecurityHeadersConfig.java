package co.com.luisgomez29.api.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersConfig implements WebFilter {

    private static final String CSP = "default-src 'none'; img-src 'self'; script-src 'self'; style-src 'self'; " +
            "object-src 'none'; frame-ancestors 'none'; upgrade-insecure-requests";

    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        HttpHeaders headers = exchange.getResponse().getHeaders();
        headers.set("Content-Security-Policy", CSP);
        headers.set("Strict-Transport-Security", "max-age=31536000; includeSubDomains; preload");
        headers.set("X-Content-Type-Options", "nosniff");
        headers.set("Server", "");
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        headers.set("Referrer-Policy", "strict-origin-when-cross-origin");
        return chain.filter(exchange);
    }
}