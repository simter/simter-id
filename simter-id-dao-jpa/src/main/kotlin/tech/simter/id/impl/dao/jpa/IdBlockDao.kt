package tech.simter.id.impl.dao.jpa

/**
 * The id block Dao Interface.
 *
 * @author RJ
 */
interface IdBlockDao {
  /**
   * Get the next id for the specific type.
   *
   * @param[t] the type
   * @return the next id
   */
  fun nextLong(t: String): Long
}