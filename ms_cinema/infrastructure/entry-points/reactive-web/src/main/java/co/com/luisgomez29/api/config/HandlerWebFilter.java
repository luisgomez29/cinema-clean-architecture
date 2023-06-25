package co.com.luisgomez29.api.config;

import co.com.luisgomez29.model.common.exception.TechnicalException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.ACCEPT_HEADER_INVALID;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.HEADER_MISSING_ERROR;

@Component
public class HandlerWebFilter implements WebFilter {
    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        final List<String> acceptHeaders = exchange.getRequest().getHeaders().get("Accept");

        if (acceptHeaders == null) {
            return Mono.error(new TechnicalException(HEADER_MISSING_ERROR));
        }

        if (!MediaType.APPLICATION_JSON_VALUE.equals(acceptHeaders.get(0))) {
            return Mono.error(new TechnicalException(ACCEPT_HEADER_INVALID));
        }

        return chain.filter(exchange);
    }

}
