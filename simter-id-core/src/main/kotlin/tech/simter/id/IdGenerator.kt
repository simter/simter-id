package tech.simter.id

/**
 * An id generator interface.
 *
 * @param <R> the id type
 * @author RJ
 */
interface IdGenerator<R> {
  /**
   * Generate a next id.
   */
  fun nextId(): R
}