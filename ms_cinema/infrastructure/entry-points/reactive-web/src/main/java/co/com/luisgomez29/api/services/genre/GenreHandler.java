package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.dto.GenreDTO;
import co.com.luisgomez29.api.dto.ResponseDTO;
import co.com.luisgomez29.api.handlers.ValidatorHandler;
import co.com.luisgomez29.api.mapper.GenreDTOMapper;
import co.com.luisgomez29.api.util.ParamsUtil;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.usecase.cinema.CinemaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.INVALID_BODY_PARAMETER;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class GenreHandler {
    private final CinemaUseCase useCase;
    private final ValidatorHandler validatorHandler;
    private final GenreDTOMapper mapper;

    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return useCase.getAll()
                .collectList()
                .map(genres -> ResponseDTO.success(genres, serverRequest))
                .flatMap(ResponseDTO::responseOk);
    }

    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        return ParamsUtil.getIdAsInt(serverRequest)
                .flatMap(useCase::getById)
                .map(genre -> ResponseDTO.success(genre, serverRequest))
                .flatMap(ResponseDTO::responseOk);
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(GenreDTO.class)
                .switchIfEmpty(Mono.error(new TechnicalException(INVALID_BODY_PARAMETER)))
                .doOnNext(validatorHandler::validateObject)
                .map(mapper::toEntity)
                .flatMap(useCase::save)
                .map(genre -> ResponseDTO.success(genre, serverRequest))
                .flatMap(ResponseDTO::responseOk);
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        return Mono.zip(ParamsUtil.getIdAsInt(serverRequest), serverRequest.bodyToMono(GenreDTO.class))
                .switchIfEmpty(Mono.error(new TechnicalException(INVALID_BODY_PARAMETER)))
                .flatMap(t -> Mono.just(t.getT2())
                        .doOnNext(validatorHandler::validateObject)
                        .map(mapper::toEntity)
                        .flatMap(genre -> useCase.update(t.getT1(), genre))
                        .map(genre -> ResponseDTO.success(genre, serverRequest))
                        .flatMap(ResponseDTO::responseOk));
    }

}
