package co.com.luisgomez29

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
open class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}
