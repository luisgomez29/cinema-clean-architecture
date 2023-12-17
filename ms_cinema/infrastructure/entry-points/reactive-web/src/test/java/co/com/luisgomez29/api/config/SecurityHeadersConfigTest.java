package co.com.luisgomez29.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SecurityHeadersConfigTest {

    @InjectMocks
    private SecurityHeadersConfig securityHeadersConfig;

    private WebFilterChain filterChain;

    @BeforeEach
    void setUp() {
        filterChain = filterExchange -> Mono.empty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"/swagger", "/api-docs"})
    void isSwaggerPathShouldNotAddResponseHeaders(String path) {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get(path)
                .build());

        StepVerifier.create(securityHeadersConfig.filter(exchange, filterChain))
                .verifyComplete();
    }

    @Test
    void setHeadersSuccessful() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/")
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build());

        StepVerifier.create(securityHeadersConfig.filter(exchange, filterChain))
                .verifyComplete();
    }
}