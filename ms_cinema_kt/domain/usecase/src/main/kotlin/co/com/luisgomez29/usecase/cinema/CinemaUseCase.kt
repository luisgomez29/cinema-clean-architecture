package co.com.luisgomez29.usecase.cinema

import co.com.luisgomez29.model.genre.Genre
import co.com.luisgomez29.model.genre.gateways.GenreRepository
import kotlinx.coroutines.flow.Flow

class CinemaUseCase(private val genreRepository: GenreRepository) {

    fun getAll(): Flow<Genre> {
        return genreRepository.findAllGenres()
    }

}
