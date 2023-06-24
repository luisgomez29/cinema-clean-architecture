package co.com.luisgomez29.api.handlers;

import co.com.luisgomez29.model.common.exception.TechnicalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.HEADER_MISSING_ERROR;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.INVALID_BODY_PARAMETER;

@Component
@RequiredArgsConstructor
@Log4j2
public class ValidatorHandler {

    private final Validator validator;

    private <T> void validateConstraints(Set<ConstraintViolation<T>> constraints) {
        if (!constraints.isEmpty()) {
            log.error(new TechnicalException(getMessage(constraints), INVALID_BODY_PARAMETER));
            throw new TechnicalException(getMessage(constraints), INVALID_BODY_PARAMETER);
        }
    }

    public <T> void validateObject(T object) {
        Set<ConstraintViolation<T>> constraints = validator.validate(object);
        validateConstraints(constraints);
    }

    public <T> void validateObject(T object, Class<?> clazz) {
        Set<ConstraintViolation<T>> constraints = validator.validate(object, clazz);
        validateConstraints(constraints);
    }

    public <T> void validateObjectHeaders(T object) {
        Set<ConstraintViolation<T>> constraints = validator.validate(object);
        if (!constraints.isEmpty()) {
            throw new TechnicalException(getMessage(constraints), HEADER_MISSING_ERROR);
        }
    }

    private <T> String getMessage(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(c -> String.join(" ", c.getPropertyPath().toString(), c.getMessage()))
                .collect(Collectors.joining(", "));
    }

}
