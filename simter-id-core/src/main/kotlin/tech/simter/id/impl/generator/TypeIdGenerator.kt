package tech.simter.id.impl.generator

import reactor.core.publisher.Mono
import tech.simter.id.core.IdDao
import tech.simter.id.core.IdGenerator

/**
 * An auto increment [Long] [IdGenerator] base on the specific type.
 *
 *
 * Each type's id is start from 1 and step 1.
 *
 *
 * This [IdGenerator] use [IdDao.nextLong] to get the next id.
 *
 * @author RJ
 * @see IdDao.nextLong
 */
class TypeIdGenerator(private val type: String, private val idDao: IdDao) : IdGenerator<Mono<Long>> {
  override fun nextId(): Mono<Long> {
    return idDao.nextLong(type)
  }
}