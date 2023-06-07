package co.com.luisgomez29.model.genre.gateways;

import co.com.luisgomez29.model.genre.Genre;
import reactor.core.publisher.Flux;

public interface GenreRepository {
    Flux<Genre> findAllGenre();
}
