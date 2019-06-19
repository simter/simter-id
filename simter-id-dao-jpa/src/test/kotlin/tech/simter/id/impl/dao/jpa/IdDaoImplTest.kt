package tech.simter.id.impl.dao.jpa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.kotlin.test.test
import tech.simter.id.core.IdDao
import tech.simter.id.impl.dao.jpa.TestHelper.randomIdHolder
import tech.simter.reactive.jpa.ReactiveEntityManager
import tech.simter.reactive.test.jpa.ReactiveDataJpaTest
import tech.simter.util.RandomUtils.randomString


/**
 * @author RJ
 */
@SpringJUnitConfig(UnitTestConfiguration::class)
@ReactiveDataJpaTest
class IdDaoImplTest @Autowired constructor(
  val rem: ReactiveEntityManager,
  val dao: IdDao
) {
  @Test
  fun `nextLong should start from 1`() {
    dao.nextLong(randomString()).test().expectNext(1L).verifyComplete()
  }

  @Test
  fun `nextLong should plus 1`() {
    // prepare data
    val id = randomIdHolder()
    rem.persist(id).test().verifyComplete()

    // invoke and verify
    dao.nextLong(id.t).test().expectNext(id.v + 1L).verifyComplete()
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