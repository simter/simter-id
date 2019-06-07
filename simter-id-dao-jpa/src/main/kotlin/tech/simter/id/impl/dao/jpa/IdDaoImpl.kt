package tech.simter.id.impl.dao.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import tech.simter.id.core.IdDao
import tech.simter.reactive.jpa.ReactiveJpaWrapper

/**
 * The JPA implementation of [IdDao].
 *
 * @author RJ
 */
@Repository
class IdDaoImpl @Autowired constructor(
  private val blockDao: IdBlockDao,
  private val wrapper: ReactiveJpaWrapper
) : IdDao {
  override fun nextLong(t: String): Mono<Long> {
    return wrapper.fromCallable { blockDao.nextLong(t) }
  }
}