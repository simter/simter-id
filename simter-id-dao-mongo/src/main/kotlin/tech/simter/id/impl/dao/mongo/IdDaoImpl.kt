package tech.simter.id.impl.dao.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.ReactiveMongoTransactionManager
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
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
  private val operations: ReactiveMongoOperations,
  private val repository: IdReactiveRepository
) : IdDao {
  @Transactional(isolation = Isolation.SERIALIZABLE)
  override fun nextLong(t: String): Mono<Long> {
    val a: ReactiveTransactionManager
    val c: MongoTransactionManager
    val e: ReactiveMongoTransactionManager
    val b: TransactionalOperator

    operations.upsert(
      Query().addCriteria(Criteria.where("id").`is`(t)),
      Update().inc("v", 1),
      IdHolderPo::class.java
    ).map {
      println("${it.matchedCount} | ${it.modifiedCount} | ${it.upsertedId}")

    }

    operations.findAndModify()


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