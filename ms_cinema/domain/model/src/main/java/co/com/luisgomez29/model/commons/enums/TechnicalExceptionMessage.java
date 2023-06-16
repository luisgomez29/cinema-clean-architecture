package co.com.luisgomez29.model.commons.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER("TE001", "Internal server error"),
    SECRET_EXCEPTION("TE002", "An error occurred while trying to get AWS secrets"),
    INVALID_BODY_PARAMETER("TE003", "Incomplete request data");

    private final String code;
    private final String detail;

}
