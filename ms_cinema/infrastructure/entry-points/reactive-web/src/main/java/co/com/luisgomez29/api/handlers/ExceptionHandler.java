package co.com.luisgomez29.api.handlers;

import co.com.luisgomez29.api.dto.ResponseDTO;
import co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage;
import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import co.com.luisgomez29.model.error.Error;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(ErrorAttributes errorAttributes,
                            WebProperties webProperties,
                            ApplicationContext applicationContext,
                            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        this.setMessageReaders(serverCodecConfigurer.getReaders());
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        return Mono.just(request)
                .map(this::getError)
                .flatMap(Mono::error)
                .onErrorResume(TechnicalException.class, this::buildErrorResponse)
                .onErrorResume(BusinessException.class, this::buildErrorResponse)
                .onErrorResume(this::buildErrorResponse)
                .cast(Error.class)
                .map(errorResponse -> errorResponse.toBuilder()
                        .domain(request.path())
                        .build())
                .flatMap(ResponseDTO::responseFail);
    }

    private Mono<Error> buildErrorResponse(TechnicalException ex) {
        return Mono.just(Error.builder()
                .reason(ex.getMessage())
                .code(ex.getTechnicalExceptionMessage().getCode())
                .message(ex.getTechnicalExceptionMessage().getMessage())
                .build()
        );
    }

    private Mono<Error> buildErrorResponse(BusinessException ex) {
        return Mono.just(Error.builder()
                .reason(ex.getMessage())
                .code(ex.getBusinessExceptionMessage().getCode())
                .message(ex.getBusinessExceptionMessage().getMessage())
                .build()
        );
    }

    private Mono<Error> buildErrorResponse(Throwable throwable) {
        return Mono.just(Error.builder()
                .reason(throwable.getMessage())
                .code(TechnicalExceptionMessage.INTERNAL_SERVER_ERROR.getCode())
                .message(TechnicalExceptionMessage.INTERNAL_SERVER_ERROR.getMessage())
                .build()
        );
    }
}
