package tech.simter.id.impl.dao.r2dbc

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.kotlin.test.test
import tech.simter.id.core.IdDao
import tech.simter.id.impl.dao.r2dbc.TestHelper.insertOne
import tech.simter.id.test.TestHelper.randomIdHolder
import tech.simter.util.RandomUtils.randomString

/**
 * @author RJ
 */
@SpringJUnitConfig(UnitTestConfiguration::class)
@DataR2dbcTest
class IdDaoImplTest @Autowired constructor(
  private val databaseClient: DatabaseClient,
  private val dao: IdDao
) {
  @Test
  fun `nextLong should start from 1`() {
    dao.nextLong(randomString()).test().expectNext(1L).verifyComplete()
  }

  @Test
  fun `nextLong should plus 1`() {
    // prepare data
    val idHolder = randomIdHolder()
    insertOne(databaseClient, idHolder).test().verifyComplete()

    // invoke and verify
    dao.nextLong(idHolder.t).test().expectNext(idHolder.v + 1L).verifyComplete()
  }

  @Test
  fun `concurrency test`() {
    val count = 100
    val idType = randomString()
    Flux.range(0, count)
      .flatMap { dao.nextLong(idType) }
      .parallel().runOn(Schedulers.parallel()).sequential()
      .collectList()
      .test()
      .consumeNextWith {
        // verify all id should not same
        assertEquals(count, it.size)
        assertEquals(count, it.toSet().size)

        // verify should step 1
        val sorted = it.toSet().sorted()
        sorted.forEachIndexed { index, v ->
          if (index < count - 1) assertEquals(v + 1, sorted[index + 1])
        }
      }
      .verifyComplete()
  }
}