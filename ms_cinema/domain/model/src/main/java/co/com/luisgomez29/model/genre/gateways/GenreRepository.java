package co.com.luisgomez29.model.genre.gateways;

import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.response.StatusResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreRepository {
    Flux<Genre> findAllGenres();

    Mono<Genre> findGenderById(Integer id);

    Mono<Genre> saveGenre(Genre genre);

    Mono<StatusResponse<Genre>> updateGenre(Genre genreFound, Genre genre);

}
