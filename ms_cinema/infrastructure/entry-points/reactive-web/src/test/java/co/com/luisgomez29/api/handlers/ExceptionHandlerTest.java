package co.com.luisgomez29.api.handlers;

import co.com.luisgomez29.model.common.exception.BusinessException;
import co.com.luisgomez29.model.common.exception.GeneralException;
import co.com.luisgomez29.model.common.exception.TechnicalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import static co.com.luisgomez29.model.common.enums.BusinessExceptionMessage.GENRE_NOT_FOUND;
import static co.com.luisgomez29.model.common.enums.GeneralExceptionMessage.PARAM_MISSING_ERROR;
import static co.com.luisgomez29.model.common.enums.TechnicalExceptionMessage.INTERNAL_SERVER_ERROR;
import static org.mockito.Mockito.mock;


@WebFluxTest
@ContextConfiguration(classes = {ExceptionHandler.class})
class ExceptionHandlerTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private WebProperties webProperties;

    @Autowired
    private ServerCodecConfigurer serverCodecConfigurer;

    private ExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ExceptionHandler(errorAttributes, webProperties, context, serverCodecConfigurer);
    }

    @Test
    void shouldGetRoutingFunction() {
        Assertions.assertNotNull(exceptionHandler.getRoutingFunction(errorAttributes));
    }

    @Test
    void shouldRenderErrorResponse() {
        ServerRequest request = mock(ServerRequest.class);
        StepVerifier.create(exceptionHandler.renderErrorResponse(request))
                .assertNext(Assertions::assertNotNull)
                .verifyComplete();
    }

    @Test
    void shouldBuildErrorBusinessExceptionResponse() {
        StepVerifier.create(exceptionHandler.buildErrorResponse(new BusinessException(GENRE_NOT_FOUND)))
                .assertNext(e -> Assertions.assertInstanceOf(Tuple2.class, e))
                .verifyComplete();
    }

    @Test
    void shouldBuildErrorTechnicalExceptionResponse() {
        StepVerifier.create(exceptionHandler.buildErrorResponse(new TechnicalException(INTERNAL_SERVER_ERROR)))
                .assertNext(e -> Assertions.assertInstanceOf(Tuple2.class, e))
                .verifyComplete();
    }

    @Test
    void shouldBuildErrorGeneralExceptionResponse() {
        StepVerifier.create(exceptionHandler.buildErrorResponse(new GeneralException(PARAM_MISSING_ERROR)))
                .assertNext(e -> Assertions.assertInstanceOf(Tuple2.class, e))
                .verifyComplete();
    }

    @Test
    void shouldBuildErrorResponseWithNOFoundStatus() {
        StepVerifier.create(exceptionHandler.buildErrorResponse(new Throwable("404 NOT_FOUND")))
                .assertNext(e -> Assertions.assertInstanceOf(Tuple2.class, e))
                .verifyComplete();
    }
}
