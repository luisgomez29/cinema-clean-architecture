package co.com.luisgomez29.api;

import co.com.luisgomez29.api.services.genre.GenreHandler
import co.com.luisgomez29.api.services.genre.GenreRouter
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

@ContextConfiguration(classes = [GenreRouter::class, GenreHandler::class])
@WebFluxTest
class GenreRouterTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun listenGETUseCase() {
        webTestClient.get()
            .uri("/api/usecase/path")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { userResponse: String? ->
                Assertions.assertThat(
                    userResponse
                ).isEqualTo("")
            }
    }

    @Test
    fun testListenGETOtherUseCase() {
        webTestClient.get()
            .uri("/api/otherusercase/path")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { userResponse: String? ->
                Assertions.assertThat(
                    userResponse
                ).isEqualTo("")
            }
    }

    @Test
    fun testListenPOSTUseCase() {
        webTestClient.post()
            .uri("/api/usecase/otherpath")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue("")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { userResponse: String? ->
                Assertions.assertThat(
                    userResponse
                ).isEqualTo("")
            }
    }
}
