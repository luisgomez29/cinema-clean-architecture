package co.com.luisgomez29.api.services.genre

import co.com.luisgomez29.api.config.ApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class GenreRouter(private val properties: ApiProperties) {

    @Bean
    open fun routerFunction(genreHandler: GenreHandler): RouterFunction<ServerResponse> {
        return coRouter {
            GET(properties.genre, accept(MediaType.APPLICATION_JSON), genreHandler::finAll)
        }
    }
}
