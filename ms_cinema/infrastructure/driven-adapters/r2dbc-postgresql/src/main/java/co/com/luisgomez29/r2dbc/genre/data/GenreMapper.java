package co.com.luisgomez29.r2dbc.genre.data;

import co.com.luisgomez29.model.genre.Genre;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface GenreMapper {

    Genre toEntity(GenreData genreData);

    GenreData toData(Genre genre);
}
