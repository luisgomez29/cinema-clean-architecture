package co.com.luisgomez29.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

@Configuration
@ComponentScan(
basePackages = ["co.com.luisgomez29.usecase"],
includeFilters = [ComponentScan.Filter(type = FilterType.REGEX, pattern = ["^.+UseCase$"])],
useDefaultFilters = false
)
open class UseCasesConfig
