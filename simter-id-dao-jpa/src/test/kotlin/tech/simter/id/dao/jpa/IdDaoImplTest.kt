package tech.simter.id.dao.jpa

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import reactor.test.test
import tech.simter.id.dao.IdDao
import tech.simter.id.dao.jpa.TestHelper.randomIdHolder
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
}