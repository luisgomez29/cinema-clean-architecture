package co.com.luisgomez29.genre.data

import co.com.luisgomez29.model.genre.Genre
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface GenreMapper {
    fun toEntity(genreData: GenreData): Genre
    fun toData(genre: Genre): GenreData
}
