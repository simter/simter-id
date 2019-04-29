package tech.simter.id.generator.impl;

import tech.simter.id.generator.IdGenerator;

import java.util.UUID;

/**
 * A random UUID id generator.
 * <p>
 * It's thread-safe for distribute system.
 *
 * @author RJ
 */
public class UUIDIdGenerator implements IdGenerator<UUID> {
  @Override
  public UUID nextId() {
    return UUID.randomUUID();
  }
}