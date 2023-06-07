package co.com.luisgomez29.usecase.cinema;

import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class CinemaUseCase {

    private final GenreRepository genreRepository;

    public Flux<Genre> getAll() {
        return genreRepository.findAllGenre();
    }

}
