package co.com.luisgomez29.api.mapper;

import co.com.luisgomez29.api.dto.GenreDTO;
import co.com.luisgomez29.model.genre.Genre;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenreDTOMapperTest {


    @InjectMocks
    private GenreDTOMapperImpl mapper;

    @Test
    void toEntitySuccess() {
        var genreDTO = GenreDTO.builder()
                .name("Comedia")
                .build();

        assertThat(mapper.toEntity(genreDTO)).isInstanceOf(Genre.class);
    }

    @Test
    void toEntityWithNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }


}
