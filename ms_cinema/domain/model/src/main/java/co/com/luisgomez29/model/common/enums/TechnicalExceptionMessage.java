package co.com.luisgomez29.model.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER_ERROR("TE001", "Internal server error"),
    SECRET_EXCEPTION("TE002", "An error occurred while trying to get AWS secrets"),
    JSON_PROCESSING("TE002", "Error processing request for logs"),

    GENRE_FIND_ALL("TE008", "Error getting all genres"),
    GENRE_FIND_BY_ID("TE009", "Error getting genre by id"),
    GENRE_SAVE("TE0010", "Error saving genre"),
    GENRE_UPDATE("TE011", "Error updating genre"),
    GENRE_DELETE("TE012", "Error deleting genre");

    private final String code;
    private final String message;

}
