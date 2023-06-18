package co.com.luisgomez29.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    BAD_REQUEST_BODY("BE001", "Error in body request"),
    GENRE_NOT_FOUND("BE001", "Genre not found");

    private final String code;
    private final String message;
}
