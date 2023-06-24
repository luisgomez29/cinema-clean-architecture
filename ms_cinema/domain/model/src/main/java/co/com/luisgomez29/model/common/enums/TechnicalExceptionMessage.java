package co.com.luisgomez29.model.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER_ERROR("TE001", "Internal server error"),
    SECRET_EXCEPTION("TE002", "An error occurred while trying to get AWS secrets"),
    INVALID_BODY_PARAMETER("TE003", "Incomplete request data"),
    HEADER_MISSING_ERROR("TE004", "Missing parameters in the header"),
    PARAM_MISSING_ERROR("TE005", "Missing parameters"),
    PARAM_WRONG_VALUE_ERROR("TE006", "Wrong value for parameter"),

    GENRE_FIND_ALL("TE007", "Error getting all genres"),
    GENRE_FIND_BY_ID("TE008", "Error getting genre by id"),
    GENRE_SAVE("TE009", "Error saving genre"),
    GENRE_UPDATE("TE010", "Error updating genre"),
    GENRE_DELETE("TE011", "Error deleting genre");

    private final String code;
    private final String message;

}
