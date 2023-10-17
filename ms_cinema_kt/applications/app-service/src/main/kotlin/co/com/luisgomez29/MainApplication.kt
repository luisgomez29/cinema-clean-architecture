package co.com.luisgomez29

import co.com.luisgomez29.genre.GenreRepositoryAdapter
import co.com.luisgomez29.usecase.cinema.CinemaUseCase
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
open class MainApplication

suspend fun main(args: Array<String>) {
    val context = runApplication<MainApplication>(*args)
    val useCase = CinemaUseCase(context.getBean(GenreRepositoryAdapter::class.java))
    useCase.getAll()
}
