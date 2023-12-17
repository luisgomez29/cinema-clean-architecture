package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.dto.GenreDTO;
import co.com.luisgomez29.api.dto.ResponseDTO;
import co.com.luisgomez29.api.handlers.ValidatorHandler;
import co.com.luisgomez29.api.mapper.GenreDTOMapper;
import co.com.luisgomez29.api.util.ParamsUtil;
import co.com.luisgomez29.model.common.exception.GeneralException;
import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.GeneralExceptionMessage.INVALID_BODY_PARAMETER;

@Component
@RequiredArgsConstructor
public class GenreHandler {
    private final CinemaUseCase useCase;
    private final ValidatorHandler validatorHandler;
    private final GenreDTOMapper mapper;

    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return useCase.getAll()
                .collectList()
                .flatMap(genres -> ResponseDTO.success(serverRequest, genres));
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        return ParamsUtil.getIdAsInt(serverRequest)
                .flatMap(useCase::getById)
                .flatMap(genre -> ResponseDTO.success(serverRequest, genre));
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GenreDTO.class)
                .switchIfEmpty(Mono.error(new GeneralException(INVALID_BODY_PARAMETER)))
                .doOnNext(validatorHandler::validateObject)
                .map(mapper::toEntity)
                .flatMap(useCase::save)
                .flatMap(genre -> ResponseDTO.success(serverRequest, genre));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return Mono.zip(ParamsUtil.getIdAsInt(serverRequest), serverRequest.bodyToMono(GenreDTO.class))
                .switchIfEmpty(Mono.error(new GeneralException(INVALID_BODY_PARAMETER)))
                .flatMap(t -> Mono.just(t.getT2())
                        .doOnNext(validatorHandler::validateObject)
                        .map(mapper::toEntity)
                        .flatMap(genre -> useCase.update(t.getT1(), genre))
                        .flatMap(genre -> ResponseDTO.success(serverRequest, genre))
                );
    }

}
