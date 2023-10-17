package co.com.luisgomez29.model.movie

import co.com.luisgomez29.model.genre.Genre
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDateTime

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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