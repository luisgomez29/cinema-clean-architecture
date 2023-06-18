package co.com.luisgomez29.api.util;

import co.com.luisgomez29.model.common.exception.TechnicalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.HEADER_MISSING_ERROR;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.PARAM_MISSING_ERROR;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.PARAM_WRONG_VALUE_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParamsUtil {
    public static final String ID = "id";

    private static Mono<String> ofEmptyHeaders(String value) {
        return (Objects.isNull(value) || value.isEmpty()) ?
                Mono.error(new TechnicalException(HEADER_MISSING_ERROR)) : Mono.just(value);
    }

    private static Mono<String> ofEmptyParams(String value) {
        return (Objects.isNull(value) || value.isEmpty()) ?
                Mono.error(new TechnicalException(PARAM_MISSING_ERROR)) : Mono.just(value);
    }

    public static Mono<String> getId(ServerRequest request) {
        return ofEmptyParams(request.pathVariable(ID));
    }

    public static Mono<Integer> getIdAsInt(ServerRequest request) {
        return ofEmptyParams(request.pathVariable(ID))
                .map(Integer::valueOf)
                .onErrorMap(NumberFormatException.class, e -> new TechnicalException(PARAM_WRONG_VALUE_ERROR));
    }
}
