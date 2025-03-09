package co.com.luisgomez29.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    GENRE_NOT_FOUND("BE001", "Genre not found"),
    RATE_LIMIT_EXCEPTION("BE002", "Requests exceeded quota");

    private final String code;
    private final String message;
}
