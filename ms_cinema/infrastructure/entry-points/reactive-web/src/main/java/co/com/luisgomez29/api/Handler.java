package co.com.luisgomez29.api;

import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final CinemaUseCase useCase;

    public Mono<ServerResponse> listGenres(ServerRequest serverRequest) {
        return useCase.getAll()
                .collectList()
                .flatMap(genres -> ServerResponse.ok().bodyValue(genres));
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }
}
