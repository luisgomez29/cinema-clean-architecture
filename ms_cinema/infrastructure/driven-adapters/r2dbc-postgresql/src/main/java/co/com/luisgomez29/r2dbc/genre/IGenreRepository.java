package co.com.luisgomez29.r2dbc.genre;

import co.com.luisgomez29.r2dbc.genre.data.GenreData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IGenreRepository extends ReactiveCrudRepository<GenreData, Integer>,
        ReactiveQueryByExampleExecutor<GenreData> {

}
