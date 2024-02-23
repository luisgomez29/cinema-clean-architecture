package co.com.luisgomez29.model.genre.gateways

import co.com.luisgomez29.model.genre.Genre
import kotlinx.coroutines.flow.Flow

fun interface GenreRepository {
    fun findAllGenres(): Flow<Genre>
}
