package co.com.luisgomez29.usecase.cinema;

import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.genre.Genre;
import co.com.luisgomez29.model.genre.gateways.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.GENRE_NOT_FOUND;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CinemaUseCaseTest {

    @InjectMocks
    private CinemaUseCase useCase;

    @Mock
    private GenreRepository genreRepository;

    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = Genre.builder()
                .id(1)
                .name("Comedia")
                .build();
    }

    @Test
    void getAllSuccess() {
        when(genreRepository.findAllGenres()).thenReturn(Flux.just(genre));

        StepVerifier.create(useCase.getAll())
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void getByIdSuccess() {
        when(genreRepository.findGenderById(anyInt())).thenReturn(Mono.just(genre));

        StepVerifier.create(useCase.getById(1))
                .expectNext(genre)
                .verifyComplete();
    }

    @Test
    void getByIdWithException() {
        when(genreRepository.findGenderById(anyInt()))
                .thenReturn(Mono.error(new BusinessException(GENRE_NOT_FOUND)));

        StepVerifier.create(useCase.getById(1))
                .expectError(BusinessException.class)
                .verify();
    }
}
