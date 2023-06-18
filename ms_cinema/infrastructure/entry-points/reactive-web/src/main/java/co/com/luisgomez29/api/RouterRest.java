package co.com.luisgomez29.api;

import co.com.luisgomez29.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RouterRest {

    private final ApiProperties apiProperties;

    private static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return nest(path(apiProperties.basePath()),
                route(GET(apiProperties.genre()), handler::listGenres)
                        .andRoute(GET(apiProperties.genre().concat(ID)), handler::getGenreById));
    }
}
