package tech.simter.id.impl.dao.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.stereotype.Repository
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
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
  //private val operations: ReactiveMongoOperations,
  private val repository: IdReactiveRepository
) : IdDao {
  @Transactional(isolation = Isolation.SERIALIZABLE)
  override fun nextLong(t: String): Mono<Long> {
    val a: ReactiveTransactionManager
    val c: MongoTransactionManager
    val e: ReactiveMongoTransactionManager
    val operations: ReactiveMongoOperations
    val b: TransactionalOperator
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