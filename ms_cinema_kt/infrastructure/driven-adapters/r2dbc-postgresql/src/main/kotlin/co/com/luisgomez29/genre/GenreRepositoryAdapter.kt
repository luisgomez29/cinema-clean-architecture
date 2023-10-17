package co.com.luisgomez29.genre

import co.com.luisgomez29.genre.data.GenreData
import co.com.luisgomez29.genre.data.GenreMapper
import co.com.luisgomez29.helper.ReactiveAdapterOperations
import co.com.luisgomez29.model.genre.Genre
import co.com.luisgomez29.model.genre.gateways.GenreRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Repository


@Repository
open class GenreRepositoryAdapter(repository: IGenreRepository, mapper: GenreMapper) :
    ReactiveAdapterOperations<Genre, GenreData, Int, IGenreRepository>(
        repository,
        mapper::toData,
        mapper::toEntity
    ),
    GenreRepository {

    override fun findAllGenres(): Flow<Genre> {

        return super.findAll()
    }
}