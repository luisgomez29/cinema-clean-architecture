package co.com.luisgomez29.r2dbc.genre;

import co.com.luisgomez29.model.common.enums.Response;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.response.StatusResponse;
import co.com.luisgomez29.r2dbc.genre.data.GenreData;
import co.com.luisgomez29.r2dbc.genre.data.GenreMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreRepositoryAdapterTest {

    @InjectMocks
    private GenreRepositoryAdapter repositoryAdapter;
    @Mock
    private IGenreRepository repository;
    @Mock
    private GenreMapper mapper;

    private Genre genre;
    private GenreData genreData;

    @BeforeEach
    void setUp() {
        genre = Genre.builder()
                .id(1)
                .name("Comedia")
                .build();

        genreData = GenreData.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    @Test
    void mustFindAllGenres() {
        when(repository.findAll()).thenReturn(Flux.just(genreData));
        when(mapper.toEntity(any())).thenReturn(genre);

        StepVerifier.create(repositoryAdapter.findAllGenres())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void mustFindAllGenresWithException() {
        when(repository.findAll()).thenReturn(Flux.error(RuntimeException::new));

        StepVerifier.create(repositoryAdapter.findAllGenres())
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    void mustFindGenreById() {
        when(repository.findById(anyInt())).thenReturn(Mono.just(genreData));
        when(mapper.toEntity(any())).thenReturn(genre);

        StepVerifier.create(repositoryAdapter.findGenderById(genre.getId()))
                .expectNextMatches(value -> genre.getName().equals(value.getName()))
                .verifyComplete();
    }

    @Test
    void mustFindGenreByIdWithException() {
        when(repository.findById(anyInt())).thenReturn(Mono.error(RuntimeException::new));

        StepVerifier.create(repositoryAdapter.findGenderById(1))
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    void mustSaveGenre() {
        when(repository.save(any())).thenReturn(Mono.just(genreData));
        when(mapper.toEntity(any())).thenReturn(genre);

        StepVerifier.create(repositoryAdapter.saveGenre(genre))
                .expectNext(genre)
                .verifyComplete();
    }

    @Test
    void mustSaveGenreWithException() {
        when(repository.save(any())).thenReturn(Mono.error(RuntimeException::new));

        StepVerifier.create(repositoryAdapter.saveGenre(genre))
                .expectError(TechnicalException.class)
                .verify();
    }

    @Test
    void mustUpdateGenre() {
        var newGenre = Genre.builder()
                .id(1)
                .name("Terror")
                .build();

        var newGenreData = GenreData.builder()
                .id(newGenre.getId())
                .name(newGenre.getName())
                .build();

        var response = StatusResponse.<Genre>builder()
                .before(genre)
                .actual(newGenre)
                .description(Response.SUCCESSFUL_UPGRADE.getDescription()).build();

        when(repository.save(any())).thenReturn(Mono.just(newGenreData));
        when(mapper.toEntity(any())).thenReturn(newGenre);

        StepVerifier.create(repositoryAdapter.updateGenre(genre, newGenre))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void mustUpdateGenreWithException() {
        when(repository.save(any())).thenReturn(Mono.error(RuntimeException::new));

        StepVerifier.create(repositoryAdapter.updateGenre(genre, genre))
                .expectError(TechnicalException.class)
                .verify();
    }
}
