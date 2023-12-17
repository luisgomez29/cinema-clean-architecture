package co.com.luisgomez29.api.handlers;

import co.com.luisgomez29.api.dto.ErrorDTO;
import co.com.luisgomez29.api.dto.ResponseDTO;
import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.common.exception.GeneralException;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;


@Component
@Order(-2)
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    private static final String PROJECT_NAME = "ms_cinema";

    public ExceptionHandler(ErrorAttributes errorAttributes, WebProperties webProperties,
                            ApplicationContext applicationContext,
                            ServerCodecConfigurer configurator) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageReaders(configurator.getReaders());
        this.setMessageWriters(configurator.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(BusinessException.class, this::buildErrorResponse)
                .onErrorResume(TechnicalException.class, this::buildErrorResponse)
                .onErrorResume(GeneralException.class, this::buildErrorResponse)
                .onErrorResume(this::buildErrorResponse)
                .cast(Tuple2.class)
                .flatMap(error -> ResponseDTO.failed(request, error.get(0), (HttpStatus) error.get(1)));
    }

    protected Mono<Tuple2<ErrorDTO, HttpStatus>> buildErrorResponse(BusinessException ex) {
        return Mono.just(ErrorDTO.builder()
                        .id(ex.getBusinessExceptionMessage().getCode())
                        .title(ex.getBusinessExceptionMessage().toString())
                        .type("Business")
                        .message(ex.getBusinessExceptionMessage().getMessage())
                        .source(PROJECT_NAME)
                        .build())
                .map(e -> Tuples.of(e, HttpStatus.CONFLICT));
    }

    protected Mono<Tuple2<ErrorDTO, HttpStatus>> buildErrorResponse(TechnicalException ex) {
        return Mono.just(ErrorDTO.builder()
                        .id(ex.getTechnicalExceptionMessage().getCode())
                        .title(ex.getTechnicalExceptionMessage().toString())
                        .type("Technical")
                        .message(ex.getTechnicalExceptionMessage().getMessage())
                        .source(PROJECT_NAME)
                        .build())
                .map(e -> Tuples.of(e, HttpStatus.INTERNAL_SERVER_ERROR));
    }

    protected Mono<Tuple2<ErrorDTO, HttpStatus>> buildErrorResponse(GeneralException ex) {
        return Mono.just(ErrorDTO.builder()
                        .id(ex.getGeneralExceptionMessage().getCode())
                        .title(ex.getGeneralExceptionMessage().toString())
                        .type("General")
                        .message(ex.getGeneralExceptionMessage().getMessage())
                        .source(PROJECT_NAME)
                        .build())
                .map(e -> Tuples.of(e, HttpStatus.resolve(ex.getGeneralExceptionMessage().getStatusCode())));
    }

    protected Mono<Tuple2<ErrorDTO, HttpStatus>> buildErrorResponse(Throwable throwable) {
        return Mono.just(ErrorDTO.builder()
                        .message(throwable.getMessage())
                        .title(throwable.getMessage())
                        .source(PROJECT_NAME)
                        .build())
                .map(e -> {
                    if (HttpStatus.NOT_FOUND.toString().equals(e.getMessage()))
                        return Tuples.of(e, HttpStatus.NOT_FOUND);
                    return Tuples.of(e, HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

}
