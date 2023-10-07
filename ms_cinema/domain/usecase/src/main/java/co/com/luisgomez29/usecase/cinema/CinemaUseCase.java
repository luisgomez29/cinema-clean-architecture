package co.com.luisgomez29.usecase.cinema;

import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
import co.com.luisgomez29.model.response.ResponseStatus;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.GENRE_NOT_FOUND;

@RequiredArgsConstructor
public class CinemaUseCase {

    private final GenreRepository genreRepository;

    public Flux<Genre> getAll() {
        return genreRepository.findAllGenres();
    }

    public Mono<Genre> getById(Integer id) {
        return genreRepository.findGenderById(id)
                .switchIfEmpty(Mono.error(new BusinessException(GENRE_NOT_FOUND)));
    }

    public Mono<Genre> save(Genre genre) {
        return genreRepository.saveGenre(genre);
    }

    public Mono<ResponseStatus<Genre>> update(Integer id, Genre genre) {
        return this.getById(id)
                .flatMap(genreFound -> genreRepository.updateGenre(genreFound, genre));
    }
}
