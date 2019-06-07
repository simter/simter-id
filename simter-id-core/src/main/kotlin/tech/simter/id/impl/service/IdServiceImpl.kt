package tech.simter.id.impl.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import tech.simter.id.OPERATION_NEXT
import tech.simter.id.PACKAGE
import tech.simter.id.core.IdDao
import tech.simter.id.core.IdService
import tech.simter.reactive.security.ModuleAuthorizer

/**
 * The ID service implementation.
 *
 * @author RJ
 */
@Service
class IdServiceImpl @Autowired constructor(
  @Qualifier("$PACKAGE.service.ModuleAuthorizer")
  private val moduleAuthorizer: ModuleAuthorizer,
  private val dao: IdDao
) : IdService {
  override fun nextLong(t: String): Mono<Long> {
    return moduleAuthorizer
      .verifyHasPermission(OPERATION_NEXT)
      .then(dao.nextLong(t))
  }
}