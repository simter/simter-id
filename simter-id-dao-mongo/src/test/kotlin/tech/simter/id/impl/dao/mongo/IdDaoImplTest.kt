package tech.simter.id.impl.dao.mongo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
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
}