package tech.simter.id.generator.impl

import tech.simter.id.IdGenerator

import java.util.UUID

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