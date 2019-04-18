package tech.simter.id.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tech.simter.id.dao.IdDao

/**
 * The ID service implementation.
 *
 * @author RJ
 */
@Service
class IdServiceImpl @Autowired constructor(
  private val dao: IdDao
) : IdService {
  override fun nextLong(t: String): Mono<Long> {
    return dao.nextLong(t)
  }
}