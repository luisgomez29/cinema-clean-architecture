package co.com.luisgomez29.secretsmanager;

import co.com.bancolombia.secretsmanager.connector.AWSSecretManagerConnectorAsync;
import co.com.luisgomez29.model.commons.enums.SuccessMessage;
import co.com.luisgomez29.model.commons.enums.TechnicalExceptionMessage;
import co.com.luisgomez29.model.commons.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
@Log4j2
public class SecretsManager {

    private final AWSSecretManagerConnectorAsync awsSecretManagerConnectorAsync;

    public <T> Mono<T> getSecret(String secretName, Class<T> cls) {
        return awsSecretManagerConnectorAsync.getSecret(secretName, cls)
                .doOnSuccess(e -> log.info(SuccessMessage.GET_SECRET.getMessage()))
                .onErrorMap(error -> {
                    log.error(String.format("ERROR! --> %s", error.getMessage()));
                    return new TechnicalException(TechnicalExceptionMessage.SECRET_EXCEPTION);
                });
    }
}
