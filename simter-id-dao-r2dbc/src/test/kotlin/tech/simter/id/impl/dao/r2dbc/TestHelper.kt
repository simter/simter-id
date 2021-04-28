package tech.simter.id.impl.dao.r2dbc

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import tech.simter.id.TABLE_ID
import tech.simter.id.core.IdHolder

object TestHelper {
  fun insertOne(databaseClient: DatabaseClient, idHolder: IdHolder): Mono<Void> {
    return databaseClient.sql("insert into $TABLE_ID (t, v) values (:type, :value)")
      .bind("type", idHolder.t)
      .bind("value", idHolder.v)
      .then()
  }
}