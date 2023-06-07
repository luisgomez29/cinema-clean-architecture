package co.com.luisgomez29.r2dbc;

import co.com.luisgomez29.r2dbc.genre.GenreRepositoryAdapter;
import co.com.luisgomez29.r2dbc.genre.IGenreRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GenreDataRepositoryAdapterTest {
    // TODO: change four you own tests

    @InjectMocks
    GenreRepositoryAdapter repositoryAdapter;

    @Mock
    IGenreRepository repository;

//    @Test
//    void mustFindValueById() {
//
//        when(repository.findById(1)).thenReturn(Mono.just(1));
//        when(mapper.map("test", Object.class)).thenReturn("test");
//
//        Mono<Object> result = repositoryAdapter.findById("1");
//
//        StepVerifier.create(result)
//                .expectNextMatches(value -> value.equals("test"))
//                .verifyComplete();
//    }
}
