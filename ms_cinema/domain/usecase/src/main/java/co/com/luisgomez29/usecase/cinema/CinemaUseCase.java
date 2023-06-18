package co.com.luisgomez29.usecase.cinema;

import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
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
}
