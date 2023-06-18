package co.com.luisgomez29.r2dbc.genre;

import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
import co.com.luisgomez29.r2dbc.genre.data.GenreData;
import co.com.luisgomez29.r2dbc.genre.data.GenreMapper;
import co.com.luisgomez29.r2dbc.helper.ReactiveAdapterOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_FIND_ALL;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.GENRE_FIND_BY_ID;

@Repository
public class GenreRepositoryAdapter extends ReactiveAdapterOperations<Genre, GenreData, Integer, IGenreRepository>
        implements GenreRepository {

    public GenreRepositoryAdapter(IGenreRepository repository, GenreMapper mapper) {
        super(repository, mapper::toData, mapper::toEntity);
    }

    @Override
    public Flux<Genre> findAllGenres() {
        return super.findAll()
                .onErrorMap(e -> new TechnicalException(e, GENRE_FIND_ALL));
    }

    @Override
    public Mono<Genre> findGenderById(Integer id) {
        return super.findById(id)
                .onErrorMap(e -> new TechnicalException(e, GENRE_FIND_BY_ID));
    }
}
