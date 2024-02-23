package co.com.luisgomez29.api.services.genre

import co.com.luisgomez29.usecase.cinema.CinemaUseCase
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait

@Component
class GenreHandler(private val cinemaUseCase: CinemaUseCase) {

    suspend fun finAll(serverRequest: ServerRequest): ServerResponse {
        val genres = cinemaUseCase.getAll()
        return ServerResponse.ok().contentType(APPLICATION_JSON).bodyAndAwait(genres);
    }
}
