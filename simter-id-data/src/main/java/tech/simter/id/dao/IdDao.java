package tech.simter.id.dao;

import reactor.core.publisher.Mono;

/**
 * The ID Dao.
 *
 * @author RJ
 */
public interface IdDao {
  /**
   * Get the next id for the specific type.
   *
   * @param t the type
   * @return the next id
   */
  Mono<Long> nextLong(String t);
}