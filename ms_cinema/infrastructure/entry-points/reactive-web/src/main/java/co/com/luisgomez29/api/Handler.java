package co.com.luisgomez29.api;

import co.com.luisgomez29.api.dto.ResponseDTO;
import co.com.luisgomez29.api.util.ParamsUtil;
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
                .flatMap(ResponseDTO::responseOk);
    }

    public Mono<ServerResponse> getGenreById(ServerRequest serverRequest) {
        return ParamsUtil.getIdAsInt(serverRequest)
                .flatMap(useCase::getById)
                .flatMap(ResponseDTO::responseOk);
    }

}
