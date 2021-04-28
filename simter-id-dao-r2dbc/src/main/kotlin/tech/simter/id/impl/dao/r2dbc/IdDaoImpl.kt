package tech.simter.id.impl.dao.r2dbc

import io.r2dbc.spi.Row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import tech.simter.id.TABLE_ID
import tech.simter.id.core.IdDao

/**
 * The r2dbc implementation of [IdDao].
 *
 * @author RJ
 */
@Repository
class IdDaoImpl @Autowired constructor(
  private val databaseClient: DatabaseClient
) : IdDao {
  override fun nextLong(t: String): Mono<Long> {
    // 1. get the current value
    return databaseClient.sql("select v from $TABLE_ID where t = :type")
      .bind("type", t)
      .map { row: Row -> Pair(false, row.get("v", Long::class.javaObjectType)!!.toLong()) }
      .one()
      .switchIfEmpty {
        // 2. create new one if not exists
        databaseClient.sql("insert into $TABLE_ID (t, v) values (:type, :value)")
          .bind("type", t)
          .bind("value", 1L)
          .then()
          .thenReturn(Pair(true, 1L))
      }
      .flatMap {
        if (it.first) Mono.just(it.second)
        else {
          // 3. update current value to the next value and return it
          databaseClient.sql("update $TABLE_ID set v = v + 1 where t = :type")
            .bind("type", t)
            .then()
            .thenReturn(it.second + 1L)
        }
      }
  }
}