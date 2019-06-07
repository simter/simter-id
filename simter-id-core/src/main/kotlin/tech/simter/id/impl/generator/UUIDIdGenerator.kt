package tech.simter.id.impl.generator

import tech.simter.id.core.IdGenerator
import java.util.*

/**
 * A random UUID id generator.
 *
 *
 * It's thread-safe for distribute system.
 *
 * @author RJ
 */
class UUIDIdGenerator : IdGenerator<UUID> {
  override fun nextId(): UUID {
    return UUID.randomUUID()
  }
}