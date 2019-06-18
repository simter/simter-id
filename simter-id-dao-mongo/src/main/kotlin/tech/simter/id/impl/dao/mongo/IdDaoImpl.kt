package tech.simter.id.impl.dao.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import tech.simter.id.core.IdDao
import tech.simter.id.impl.dao.mongo.po.IdHolderPo

/**
 * The Reactive MongoDB implementation of [IdDao].
 *
 * @author RJ
 */
@Repository
class IdDaoImpl @Autowired constructor(
  private val repository: IdReactiveRepository
) : IdDao {
  override fun nextLong(t: String): Mono<Long> {
    // TODO lock
    return repository.findById(t)
      // plus 1
      .map { IdHolderPo(t = it.t, v = it.v + 1L) }
      // create one if not exists
      .switchIfEmpty(Mono.just(IdHolderPo(t = t, v = 1L)))
      // save it
      .delayUntil { repository.save(it) }
      .map { it.v }
  }
}