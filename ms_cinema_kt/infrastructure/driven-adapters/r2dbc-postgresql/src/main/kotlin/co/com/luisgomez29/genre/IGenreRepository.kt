package co.com.luisgomez29.genre

import co.com.luisgomez29.genre.data.GenreData
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface IGenreRepository : CoroutineCrudRepository<GenreData, Int>