package co.com.luisgomez29.api.dto;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseDTO {

    public static <T> Mono<ServerResponse> responseOk(T response) {
        return buildResponse(HttpStatus.OK, response);
    }

    public static <T> Mono<ServerResponse> responseFail(T body) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, body);
    }

    public static <T> Mono<ServerResponse> buildResponse(HttpStatus status, T body) {
        return ServerResponse
                .status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body);
    }
}