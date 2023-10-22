package co.com.luisgomez29.model.movie

import co.com.luisgomez29.model.genre.Genre
import java.time.LocalDateTime


data class Movie(
    val id: Int,
    val genre: Genre,
    val name: String,
    val country: String,
    val duration: Float,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)