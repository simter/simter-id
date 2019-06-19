package tech.simter.id.impl.dao.mongo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers
import reactor.kotlin.test.test
import tech.simter.id.core.IdDao
import tech.simter.id.impl.dao.mongo.po.IdHolderPo
import tech.simter.util.RandomUtils.randomString

/**
 * Test [IdDaoImpl].
 *
 * @author RJ
 */
@SpringJUnitConfig(UnitTestConfiguration::class)
@DataMongoTest
class IdDaoImplTest @Autowired constructor(
  private val repository: IdReactiveRepository,
  private val dao: IdDao
) {
  @Test
  fun `nextLong should start from 1`() {
    dao.nextLong(randomString()).test().expectNext(1L).verifyComplete()
  }

  @Test
  fun `nextLong should plus 1`() {
    // prepare data
    val expected = IdHolderPo(t = randomString(), v = 123)
    repository.save(expected).block()!!

    // invoke and verify
    dao.nextLong(expected.t).test().expectNext(expected.v + 1L).verifyComplete()
  }

  @Test
  fun `concurrency test`() {
    val count = 2
    val idType = randomString()
    Flux.range(0, count)
      .flatMap { dao.nextLong(idType) }
      .parallel().runOn(Schedulers.parallel()).sequential()
      .collectList()
      .test()
      .consumeNextWith {
        // verify all id should not same
        Assertions.assertEquals(count, it.size)
        Assertions.assertEquals(count, it.toSet().size)

        // verify should step 1
        val sorted = it.toSet().sorted()
        sorted.forEachIndexed { index, v ->
          if (index < count - 1) Assertions.assertEquals(v + 1, sorted[index + 1])
        }
      }
      .verifyComplete()
  }
}