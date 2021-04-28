package tech.simter.id.impl.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import tech.simter.id.AUTHORIZER_KEY
import tech.simter.id.OPERATION_NEXT
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
  @Qualifier("$AUTHORIZER_KEY.authorizer")
  private val moduleAuthorizer: ModuleAuthorizer,
  private val dao: IdDao
) : IdService {
  @Transactional(readOnly = false)
  override fun nextLong(t: String): Mono<Long> {
    return moduleAuthorizer
      .verifyHasPermission(OPERATION_NEXT)
      .then(dao.nextLong(t))
  }
}