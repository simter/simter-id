package tech.simter.id.generator.impl

import reactor.core.publisher.Mono
import tech.simter.id.dao.IdDao
import tech.simter.id.IdGenerator

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