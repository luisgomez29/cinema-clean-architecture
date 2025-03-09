package co.com.luisgomez29.api.config;

import co.com.luisgomez29.model.common.exception.BusinessException;
import io.github.bucket4j.Bucket;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.RATE_LIMIT_EXCEPTION;

@Component
public class RateLimitFilter implements WebFilter {

    private static final int CAPACITY = 1000;
    private static final int TOKENS = 1000;

    @Override
    public @NotNull Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        var bucket = Bucket.builder()
                .addLimit(limit -> limit.capacity(CAPACITY)
                        .refillGreedy(TOKENS, Duration.ofSeconds(1))
                )
                .build();

        return chain.filter(exchange)
                .filter(rq -> bucket.tryConsume(1))
                .switchIfEmpty(Mono.error(new BusinessException(RATE_LIMIT_EXCEPTION)));
    }

}
