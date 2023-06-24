package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.BaseIntegration;
import co.com.luisgomez29.api.config.WebFluxSecurityConfig;
import co.com.luisgomez29.api.handlers.ExceptionHandler;
import co.com.luisgomez29.api.handlers.ValidatorHandler;
import co.com.luisgomez29.api.mapper.GenreDTOMapper;
import co.com.luisgomez29.model.common.enums.Response;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.response.StatusResponse;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        WebFluxSecurityConfig.class,
        ExceptionHandler.class,
        ValidatorHandler.class,
        GenreRouter.class,
        GenreHandler.class,
})
@WebFluxTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenreRouterTest extends BaseIntegration {
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
    void listGenresSuccess() {
        when(useCase.getAll()).thenReturn(Flux.just(genre));
        statusAssertionsWebClientGet(url)
                .isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

    @Test
    void getGenreByIdSuccess() {
        when(useCase.getById(anyInt())).thenReturn(Mono.just(genre));

        statusAssertionsWebClientGet(url.concat(ID), genre.getId())
                .isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

    @Test
    void saveSuccess() {
        when(useCase.save(any())).thenReturn(Mono.just(genre));
        when(mapper.toEntity(any())).thenReturn(genre);

        statusAssertionsWebClientPost(url, request)
                .isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

    @Test
    void updateSuccess() {
        var newGenre = Genre.builder()
                .id(genre.getId())
                .name("Terror")
                .build();

        var res = StatusResponse.<Genre>builder()
                .before(genre)
                .actual(newGenre)
                .description(Response.SUCCESSFUL_UPGRADE.getDescription())
                .build();

        when(useCase.update(anyInt(), any())).thenReturn(Mono.just(res));
        when(mapper.toEntity(any())).thenReturn(genre);

        statusAssertionsWebClientPut(url.concat(ID), request, genre.getId())
                .isOk()
                .expectBody(JsonNode.class)
                .value(userResponse -> assertThat(userResponse).isNotEmpty());
    }

}
