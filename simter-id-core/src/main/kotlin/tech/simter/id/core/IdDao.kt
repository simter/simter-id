package tech.simter.id.core

import reactor.core.publisher.Mono

/**
 * The ID Dao interface.
 *
 * @author RJ
 */
interface IdDao {
  /**
   * Get the next id for the specific type.
   *
   * @param[t] the type
   * @return the next id
   */
  fun nextLong(t: String): Mono<Long>
}