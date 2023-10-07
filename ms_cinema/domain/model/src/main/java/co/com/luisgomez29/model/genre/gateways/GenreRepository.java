package co.com.luisgomez29.model.genre.gateways;

import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.response.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GenreRepository {
    Flux<Genre> findAllGenres();

    Mono<Genre> findGenderById(Integer id);

    Mono<Genre> saveGenre(Genre genre);

    Mono<ResponseStatus<Genre>> updateGenre(Genre genreFound, Genre genre);

}
