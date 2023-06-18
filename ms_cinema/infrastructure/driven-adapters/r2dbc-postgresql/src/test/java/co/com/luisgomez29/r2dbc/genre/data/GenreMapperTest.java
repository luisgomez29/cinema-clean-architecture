package co.com.luisgomez29.r2dbc.genre.data;


import co.com.luisgomez29.model.genre.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GenreMapperTest {

    @InjectMocks
    private GenreMapperImpl mapper;

    private Genre genre;
    private GenreData genreData;

    @BeforeEach
    void setUp() {
        genre = Genre.builder()
                .id(1)
                .name("Comedia")
                .description("description")
                .build();

        genreData = GenreData.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }

    @Test
    void toEntitySuccess() {
        assertThat(mapper.toEntity(genreData)).isInstanceOf(Genre.class);
    }

    @Test
    void toEntityWithNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }

    @Test
    void toDataSuccess() {
        assertThat(mapper.toData(genre)).isEqualTo(genreData);
    }

    @Test
    void toDataWithNull() {
        assertThat(mapper.toData(null)).isNull();
    }

}
