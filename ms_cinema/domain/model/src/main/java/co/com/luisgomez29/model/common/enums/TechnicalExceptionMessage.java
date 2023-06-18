package co.com.luisgomez29.model.common.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER_ERROR("TE001", "Internal server error"),
    SECRET_EXCEPTION("TE002", "An error occurred while trying to get AWS secrets"),
    INVALID_BODY_PARAMETER("TE003", "Incomplete request data"),
    HEADER_MISSING_ERROR("301", "Missing parameters in the header"),
    PARAM_MISSING_ERROR("301", "Missing parameters"),
    PARAM_WRONG_VALUE_ERROR("301", "Wrong value for parameter"),

    GENRE_FIND_ALL("TE004", "Error getting all genres"),
    GENRE_FIND_BY_ID("TE005", "Error getting genre by id"),
    GENRE_SAVE("TE006", "Error saving genre"),
    GENRE_UPDATE("TE007", "Error updating genre"),
    GENRE_DELETE("TE008", "Error deleting genre");

    private final String code;
    private final String message;

}
