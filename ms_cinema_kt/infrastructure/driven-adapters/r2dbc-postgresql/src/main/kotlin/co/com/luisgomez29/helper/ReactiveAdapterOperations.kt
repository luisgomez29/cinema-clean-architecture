package co.com.luisgomez29.helper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.function.Function

abstract class ReactiveAdapterOperations<E, D, I, R>(
    private val repository: R,
    private val toDataFn: Function<E, D>,
    private val toEntityFn: Function<D, E>,
) where  R : CoroutineCrudRepository<D, I> {

    private fun toData(entity: E): D {
        return toDataFn.apply(entity)
    }

    private fun toEntity(data: D): E? {
        return if (data != null) toEntityFn.apply(data) else null
    }

    private suspend fun save(entity: E): E? {
        return toEntity(saveData(toData(entity)))
    }

    private suspend fun saveData(data: D): D {
        return repository.save(data)
    }

    protected fun saveData(data: Flow<D>): Flow<D> {
        return repository.saveAll(data)
    }

    protected suspend fun findById(id: I): E? {
        return repository.findById(id)?.let { toEntity(it) }
    }

    protected fun findAll(): Flow<E> {
        return repository.findAll().mapNotNull { toEntity(it) }
    }

}