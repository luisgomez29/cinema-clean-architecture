package co.com.luisgomez29.genre.data

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("genre")
data class GenreData(
    @Id
    val id: Int,
    val name: String,
    val description: String
)