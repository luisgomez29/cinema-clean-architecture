package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.dto.ErrorDTO;
import co.com.luisgomez29.api.dto.GenreDTO;
import co.com.luisgomez29.api.dto.ResponseDTO;
import org.springdoc.core.fn.builders.operation.Builder;

import java.util.function.Consumer;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.exampleobject.Builder.exampleOjectBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public sealed class GenreApiDoc permits GenreRouter {

    public static final String BUSINESS_ERROR = "Business Error";
    public static final String TECHNICAL_ERROR = "Technical Error";
    public static final String SUCCESS = "Success";
    public static final String ACCEPT_HEADER = "Accept Header";
    public static final String ACCEPT = "Accept";

    protected Consumer<Builder> list() {
        return ops -> ops.tag("cinema")
                .operationId("list").summary("Get all genres")
                .description("Get all genres").tags(new String[]{"genres"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .implementation(ResponseDTO.class)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder().implementation(ResponseDTO.class))
                                        .example(exampleOjectBuilder().value("""
                                                {
                                                    "meta": {
                                                        "_version": "0.0.1",
                                                        "_requestDate": "2023-12-17T15:27:53.0208008",
                                                        "_responseSize": 2,
                                                        "_requestClient": "/genre"
                                                    },
                                                    "data": [
                                                        {
                                                            "id": 1,
                                                            "name": "name",
                                                            "description": "description"
                                                        },
                                                        {
                                                            "id": 2,
                                                            "name": "description",
                                                            "description": "description"
                                                        }
                                                    ]
                                                }
                                                """)
                                        )
                        )
                )
                .response(responseBuilder().responseCode("409").description(BUSINESS_ERROR)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    protected Consumer<Builder> listById() {
        return ops -> ops.tag("cinema")
                .operationId("listById").summary("Get genres by Id")
                .description("Get genres by Id").tags(new String[]{"genres"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .parameter(createQuery(String.class, "id", "id"))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .implementation(ResponseDTO.class)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder().implementation(ResponseDTO.class))
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("409").description(BUSINESS_ERROR)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    protected Consumer<Builder> save() {
        return ops -> ops.tag("cinema")
                .operationId("save").summary("Create genre")
                .description("Create genre").tags(new String[]{"genres"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .requestBody(requestBodyBuilder().implementation(GenreDTO.class))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .implementation(ResponseDTO.class)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder().implementation(ResponseDTO.class))
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("409").description(BUSINESS_ERROR)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    protected Consumer<Builder> update() {
        return ops -> ops.tag("cinema")
                .operationId("update").summary("Update genre")
                .description("Update genre").tags(new String[]{"genres"})
                .parameter(createHeader(
                        String.class, ACCEPT, ACCEPT_HEADER, APPLICATION_JSON_VALUE
                ))
                .parameter(createQuery(String.class, "id", "id"))
                .requestBody(requestBodyBuilder().implementation(GenreDTO.class))
                .response(responseBuilder().responseCode("200").description(SUCCESS)
                        .implementation(ResponseDTO.class)
                        .content(
                                contentBuilder()
                                        .schema(schemaBuilder().implementation(ResponseDTO.class))
                                        .example(exampleResponse())
                        )
                )
                .response(responseBuilder().responseCode("409").description(BUSINESS_ERROR)
                        .implementation(ErrorDTO.class))
                .response(responseBuilder().responseCode("500").description(TECHNICAL_ERROR)
                        .implementation(ErrorDTO.class));
    }

    private <T> org.springdoc.core.fn.builders.parameter.Builder createHeader(Class<T> clazz,
                                                                              String name,
                                                                              String description,
                                                                              String example) {
        return parameterBuilder().in(HEADER).implementation(clazz).required(true).name(name).description(description)
                .example(example);
    }

    private <T> org.springdoc.core.fn.builders.parameter.Builder createQuery(Class<T> clazz,
                                                                             String name,
                                                                             String description) {
        return parameterBuilder().in(QUERY).implementation(clazz).required(true).name(name).description(description);
    }

    private <T> org.springdoc.core.fn.builders.parameter.Builder queryNoRequired(Class<T> clazz,
                                                                                 String name,
                                                                                 String description) {
        return parameterBuilder().in(QUERY).implementation(clazz).name(name).description(description);
    }

    private org.springdoc.core.fn.builders.exampleobject.Builder exampleResponse() {
        return exampleOjectBuilder().value("""
                {
                    "meta": {
                        "_version": "0.0.1",
                        "_requestDate": "2023-12-17T15:27:53.0208008",
                        "_responseSize": 1,
                        "_requestClient": "/genre"
                    },
                    "data": {
                        "id": 1,
                        "name": "name",
                        "description": "description"
                    }
                }
                """);
    }
}
