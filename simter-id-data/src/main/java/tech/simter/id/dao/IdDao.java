package tech.simter.id.dao;

/**
 * The ID Dao.
 *
 * @author RJ
 */
public interface IdDao {
  /**
   * Get the next id for the specific type.
   *
   * @param type the type
   * @return the next id
   */
  Long next(String type);
}