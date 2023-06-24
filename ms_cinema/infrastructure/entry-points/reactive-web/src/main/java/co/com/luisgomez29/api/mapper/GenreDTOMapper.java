package co.com.luisgomez29.api.mapper;

import co.com.luisgomez29.api.dto.GenreDTO;
import co.com.luisgomez29.model.genre.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreDTOMapper {

    @Mapping(target = "id", ignore = true)
    Genre toEntity(GenreDTO genreDto);

}
