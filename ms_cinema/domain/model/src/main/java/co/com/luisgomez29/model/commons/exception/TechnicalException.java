package co.com.luisgomez29.model.commons.exception;


import co.com.luisgomez29.model.commons.enums.TechnicalExceptionMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicalException extends RuntimeException {

    private final TechnicalExceptionMessage technicalExceptionMessage;

    public TechnicalException(String message, TechnicalExceptionMessage technicalException) {
        super(message);
        this.technicalExceptionMessage = technicalException;
    }

    public TechnicalException(Throwable cause, TechnicalExceptionMessage message) {
        super(cause);
        this.technicalExceptionMessage = message;
    }
}
