package co.com.luisgomez29.api.services.genre;


import co.com.luisgomez29.api.BaseIntegration;
import co.com.luisgomez29.api.config.SecurityHeadersConfig;
import co.com.luisgomez29.api.handlers.ExceptionHandler;
import co.com.luisgomez29.api.handlers.ValidatorHandler;
import co.com.luisgomez29.api.mapper.GenreDTOMapper;
import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.GENRE_NOT_FOUND;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_FIND_ALL;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_SAVE;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_UPDATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        SecurityHeadersConfig.class,
        ExceptionHandler.class,
        ValidatorHandler.class,
        GenreRouter.class,
        GenreHandler.class,
})
@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenreRouterWithExceptionTest extends BaseIntegration {
    @MockBean
    private CinemaUseCase useCase;
    @MockBean
    private GenreDTOMapper mapper;

    private final static String ID = "/{id}";
    private String url;
    private String request;
    private Genre genre;

    @BeforeAll
    void beforeAll() {
        url = properties.genre();
        request = loadFileConfig("GenreRequest.json", String.class);
    }

    @BeforeEach
    void setUp() {
        genre = Genre.builder()
                .id(1)
                .name("Comedia")
                .build();
    }

    @Test
    void listGenresWithException() {
        when(useCase.getAll())
                .thenReturn(Flux.error(new TechnicalException(GENRE_FIND_ALL)));

        statusAssertionsWebClientGet(url)
                .is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_FIND_ALL.getMessage()));
    }

    @Test
    void getGenreByIdWithException() {
        when(useCase.getById(anyInt()))
                .thenReturn(Mono.error(new BusinessException(GENRE_NOT_FOUND)));

        statusAssertionsWebClientGet(url.concat(ID), 1)
                .is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_NOT_FOUND.getMessage()));
    }

    @Test
    void saveWithException() {
        when(useCase.save(any()))
                .thenReturn(Mono.error(new TechnicalException(new RuntimeException(), GENRE_SAVE)));
        when(mapper.toEntity(any())).thenReturn(genre);

        statusAssertionsWebClientPost(url, request)
                .is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_SAVE.getMessage()));
    }

    @Test
    void updateWithException() {
        when(useCase.update(anyInt(), any()))
                .thenReturn(Mono.error(new TechnicalException(new RuntimeException(), GENRE_UPDATE)));
        when(mapper.toEntity(any())).thenReturn(genre);

        statusAssertionsWebClientPut(url.concat(ID), request, genre.getId())
                .is5xxServerError()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse.get("message").asText())
                        .isEqualTo(GENRE_UPDATE.getMessage()));
    }

}
