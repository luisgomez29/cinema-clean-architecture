package co.com.luisgomez29.model.common.exception;


import co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage;
import lombok.Getter;

@Getter
public class TechnicalException extends RuntimeException {

    private final TechnicalExceptionMessage technicalExceptionMessage;

    public TechnicalException(TechnicalExceptionMessage technicalExceptionEnum) {
        super(technicalExceptionEnum.getMessage());
        this.technicalExceptionMessage = technicalExceptionEnum;
    }

    public TechnicalException(String message, TechnicalExceptionMessage technicalException) {
        super(message);
        this.technicalExceptionMessage = technicalException;
    }

    public TechnicalException(Throwable cause, TechnicalExceptionMessage message) {
        super(cause);
        this.technicalExceptionMessage = message;
    }
}
