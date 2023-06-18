package co.com.luisgomez29.api;

import co.com.luisgomez29.api.config.ApiProperties;
import co.com.luisgomez29.api.config.WebFluxSecurityConfig;
import co.com.luisgomez29.api.handlers.ExceptionHandler;
import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.GENRE_NOT_FOUND;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_FIND_ALL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        WebFluxSecurityConfig.class,
        ExceptionHandler.class,
        RouterRest.class,
        Handler.class,
})
@EnableConfigurationProperties(ApiProperties.class)
@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ApiProperties apiProperties;
    @MockBean
    private CinemaUseCase useCase;

    private final static String ID = "/{id}";
    private String url;
    private Genre genre;

    @BeforeAll
    void beforeAll() {
        url = apiProperties.basePath().concat(apiProperties.genre());
    }

    @BeforeEach
    void setUp() {
        genre = Genre.builder()
                .id(1)
                .name("Comedia")
                .build();
    }

    @Test
    void listGenresSuccess() {
        when(useCase.getAll()).thenReturn(Flux.just(genre));

        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

    @Test
    void listGenresWithException() {
        when(useCase.getAll())
                .thenReturn(Flux.error(new TechnicalException(GENRE_FIND_ALL)));

        webTestClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_FIND_ALL.getMessage()));
    }

    @Test
    void getGenreByIdSuccess() {
        when(useCase.getById(anyInt())).thenReturn(Mono.just(genre));

        webTestClient.get()
                .uri(url.concat(ID), 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

    @Test
    void getGenreByIdWithException() {
        when(useCase.getById(anyInt()))
                .thenReturn(Mono.error(new BusinessException(GENRE_NOT_FOUND)));

        webTestClient.get()
                .uri(url.concat(ID), 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_NOT_FOUND.getMessage()));
    }
}
