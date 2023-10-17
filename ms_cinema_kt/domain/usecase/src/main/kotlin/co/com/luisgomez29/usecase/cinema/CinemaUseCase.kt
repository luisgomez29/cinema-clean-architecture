package co.com.luisgomez29.usecase.cinema

import co.com.luisgomez29.model.genre.gateways.GenreRepository

class CinemaUseCase(private val genreRepository: GenreRepository) {

    suspend fun getAll() {
        genreRepository.findAllGenres().collect { value -> println("Collected $value") }
    }

}
