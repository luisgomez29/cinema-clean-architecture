package co.com.luisgomez29.api.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;

class LogFilterTest {

    private WebFilterChain webFilterChain;
    private DataBufferFactory bufferFactory;
    private LogFilter logFilter;
    @Mock
    private ServerWebExchange exchange;
    @Mock
    private ServerHttpRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        logFilter = new LogFilter();
        webFilterChain = filterExchange -> Mono.empty();
        bufferFactory = new DefaultDataBufferFactory();
    }

    @Test
    void filterHealth() {
        exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/health")
                .build());
        StepVerifier.create(logFilter.filter(exchange, webFilterChain)).verifyComplete();
    }

    @Test
    void filterGetRequest() {
        exchange = MockServerWebExchange.from(MockServerHttpRequest
                .get("/test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .queryParam("param1", "value1")
                .build());
        StepVerifier.create(logFilter.filter(exchange, webFilterChain))
                .verifyComplete();
    }

    @Test
    void filterPostRequest() {
        exchange = MockServerWebExchange.from(MockServerHttpRequest
                .post("/test")
                .header(HttpHeaders.ACCEPT, "application/json")
                .body("{\"code\": \"test\"}"));
        StepVerifier.create(logFilter.filter(exchange, webFilterChain))
                .verifyComplete();
    }

    @Test
    void getBytesReadsDataBufferCorrectly() {
        DataBuffer dataBuffer = bufferFactory.wrap("test".getBytes(StandardCharsets.UTF_8));
        byte[] result = logFilter.getBytes(dataBuffer);
        assertArrayEquals("test".getBytes(StandardCharsets.UTF_8), result);
    }

    @Test
    void getChainFilterHandlesEmptyBody() {
        byte[] body = new byte[0];
        exchange = MockServerWebExchange.from(MockServerHttpRequest.post("/test").body("{\"code\": \"test\"}"));
        when(request.getBody()).thenReturn(Flux.just(bufferFactory.wrap(body)));
        StepVerifier.create(logFilter.getChainFilter(exchange, webFilterChain, body, request))
                .verifyComplete();
    }
}
