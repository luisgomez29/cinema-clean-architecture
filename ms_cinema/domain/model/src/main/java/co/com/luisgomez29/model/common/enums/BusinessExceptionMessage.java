package co.com.luisgomez29.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    GENRE_NOT_FOUND("BE002", "Genre not found");

    private final String code;
    private final String message;
}
