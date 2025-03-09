package co.com.luisgomez29.api.config;

import co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Log4j2
@Component
public class LogFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        var path = request.getPath().toString();

        if (path.contains("/health")) {
            return chain.filter(exchange);
        }

        var method = request.getMethod();

        if (method.equals(HttpMethod.GET)) {
            log.info("method: {}, path: {}, headers: {}, queryParams: {}",
                    method, path, getHeaders(request), request.getQueryParams());
            return chain.filter(exchange);
        }

        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> logRequest(dataBuffer, method, path, request))
                .flatMap(bytes -> getChainFilter(exchange, chain, bytes, request));
    }

    private Mono<byte[]> logRequest(DataBuffer dataBuffer, HttpMethod method, String path, ServerHttpRequest request) {
        return Mono.fromCallable(() -> {
                    try {
                        var bytes = getBytes(dataBuffer);
                        var requestBody = new String(bytes, StandardCharsets.UTF_8);
                        var mapper = new ObjectMapper();
                        JsonNode node = mapper.readTree(requestBody);
                        log.info("method: {}, path: {}, headers: {}, queryParams: {}, body: {}",
                                method, path, getHeaders(request), request.getQueryParams(),
                                mapper.writeValueAsString(node)
                        );
                        return bytes;
                    } catch (JsonProcessingException e) {
                        throw new TechnicalException(TechnicalExceptionMessage.JSON_PROCESSING);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    protected byte[] getBytes(DataBuffer dataBuffer) {
        var bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        DataBufferUtils.release(dataBuffer);
        return bytes;
    }

    protected Mono<Void> getChainFilter(
            ServerWebExchange exchange, WebFilterChain chain, byte[] bytes, ServerHttpRequest request) {
        ServerHttpRequest modifiedRequest = new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                return Flux.just(exchange.getResponse().bufferFactory().wrap(bytes));
            }
        };
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private List<Map.Entry<String, List<String>>> getHeaders(ServerHttpRequest request) {
        // Lowercase headers to exclude from logging
        var excludedHeaders = List.of(
                "authorization",
                "host",
                "x-",
                "content-length",
                "user-agent",
                "accept-encoding",
                "forwarded",
                "cookie",
                "cache-control",
                "accept-encoding",
                "postman-"
        );
        return request.getHeaders()
                .entrySet()
                .stream()
                .filter(h -> excludedHeaders.stream().noneMatch(e -> h.getKey().toLowerCase().startsWith(e)))
                .toList();
    }
}
