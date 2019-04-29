package tech.simter.id.generator;

/**
 * An id generator interface.
 *
 * @param <R> the id type
 * @author RJ
 */
public interface IdGenerator<R> {
  /**
   * Generate a next id.
   */
  R nextId();
}