package co.com.luisgomez29.api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Data
@Builder(toBuilder = true)
public class ResponseDTO<T> {

    private MetaDTO.Meta meta;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    @SuppressWarnings("rawtypes")
    public static <T> ResponseDTO success(T data, ServerRequest request) {
        return ResponseDTO.builder()
                .meta(MetaDTO.build(data, request))
                .data(data)
                .build();
    }

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