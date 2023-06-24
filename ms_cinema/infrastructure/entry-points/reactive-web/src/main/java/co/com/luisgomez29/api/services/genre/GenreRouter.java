package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class GenreRouter {

    private final ApiProperties apiProperties;

    private static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(GenreHandler handler) {
        return route()
                .GET(apiProperties.genre(), accept(MediaType.APPLICATION_JSON), handler::list)
                .GET(apiProperties.genre().concat(ID), accept(MediaType.APPLICATION_JSON), handler::getById)
                .POST(apiProperties.genre(), accept(MediaType.APPLICATION_JSON), handler::save)
                .PUT(apiProperties.genre().concat(ID), accept(MediaType.APPLICATION_JSON), handler::update)
                .build();
    }
}
