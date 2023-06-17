package co.com.luisgomez29.api;

import co.com.luisgomez29.api.config.WebFluxSecurityConfig;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {
        WebFluxSecurityConfig.class,
        RouterRest.class,
        Handler.class,
})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CinemaUseCase useCase;

    @Test
    void testListenGETUseCase() {
        when(useCase.getAll())
                .thenReturn(Flux.just(Genre.builder().id(1).name("Comedia").build()));

        webTestClient.get()
                .uri("/api/v1/genres")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> Assertions.assertThat(userResponse).isNotEmpty()
                );
    }
}
