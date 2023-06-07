package co.com.luisgomez29.r2dbc.genre;

import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
import co.com.luisgomez29.r2dbc.genre.data.GenreData;
import co.com.luisgomez29.r2dbc.genre.data.GenreMapper;
import co.com.luisgomez29.r2dbc.helper.ReactiveAdapterOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class GenreRepositoryAdapter extends ReactiveAdapterOperations<Genre, GenreData, Integer, IGenreRepository>
        implements GenreRepository {

    public GenreRepositoryAdapter(IGenreRepository repository, GenreMapper mapper) {
        super(repository, mapper::toData, mapper::toEntity);
    }

    @Override
    public Flux<Genre> findAllGenre() {
        return super.findAll();
    }
}
