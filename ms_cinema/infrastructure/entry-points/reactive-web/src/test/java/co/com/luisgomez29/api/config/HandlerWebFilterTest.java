package co.com.luisgomez29.api.config;

import co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class HandlerWebFilterTest {
    @InjectMocks
    private HandlerWebFilter handlerFilterFunction;

    private WebFilterChain filterChain;

    @BeforeEach
    void setUp() {
        filterChain = filterExchange -> Mono.empty();
    }

    @Test
    void throwExceptionIfAcceptHeaderIsNull() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/")
                .build());

        StepVerifier.create(handlerFilterFunction.filter(exchange, filterChain))
                .expectErrorMessage(TechnicalExceptionMessage.HEADER_MISSING_ERROR.getMessage())
                .verify();
    }

    @Test
    void throwExceptionIfAcceptHeaderIsNotApplicationJson() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/")
                .header("Accept", "*/*")
                .build());

        StepVerifier.create(handlerFilterFunction.filter(exchange, filterChain).log())
                .expectErrorMessage(TechnicalExceptionMessage.ACCEPT_HEADER_INVALID.getMessage())
                .verify();
    }

    @Test
    void handlerFilterFunctionSuccess() {
        var exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/")
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build());

        StepVerifier.create(handlerFilterFunction.filter(exchange, filterChain))
                .verifyComplete();
    }
}