package co.com.luisgomez29.api.services.genre;

import co.com.luisgomez29.api.config.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class GenreRouter {

    private final ApiProperties apiProperties;

    private static final String ID = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(GenreHandler handler) {
        return route(GET(apiProperties.genre()), handler::list)
                .andRoute(GET(apiProperties.genre().concat(ID)), handler::getById)
                .andRoute(POST(apiProperties.genre()), handler::save)
                .andRoute(PUT(apiProperties.genre().concat(ID)), handler::update);
    }
}
