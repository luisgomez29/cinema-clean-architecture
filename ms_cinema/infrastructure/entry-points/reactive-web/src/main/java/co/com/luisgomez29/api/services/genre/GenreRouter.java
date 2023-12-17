package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public non-sealed class GenreRouter extends GenreApiDoc {

    private final ApiProperties apiProperties;

    private static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(GenreHandler handler) {
        return route()
                .GET(apiProperties.genre(),
                        accept(MediaType.APPLICATION_JSON),
                        handler::list,
                        list())
                .build()
                .and(route()
                        .GET(
                                apiProperties.genre().concat(ID),
                                accept(MediaType.APPLICATION_JSON),
                                handler::getById,
                                listById()
                        )
                        .build()
                )
                .and(route()
                        .POST(
                                apiProperties.genre(),
                                accept(MediaType.APPLICATION_JSON),
                                handler::save,
                                save()
                        )
                        .build()
                ).and(route()
                        .PUT(
                                apiProperties.genre().concat(ID),
                                accept(MediaType.APPLICATION_JSON),
                                handler::update,
                                update()
                        )
                        .build()
                );
    }
}
