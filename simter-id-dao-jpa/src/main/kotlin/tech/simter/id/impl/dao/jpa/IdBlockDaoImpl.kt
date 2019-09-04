package tech.simter.id.impl.dao.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import tech.simter.id.impl.dao.jpa.po.IdHolderPo
import javax.persistence.EntityManagerFactory
import javax.persistence.NoResultException

/**
 * The JPA implementation of [IdBlockDao].
 *
 * @author RJ
 */
@Repository
internal class IdBlockDaoImpl @Autowired constructor(
  private val emf: EntityManagerFactory
) : IdBlockDao {
  @Synchronized
  override fun nextLong(t: String): Long {
    val em = emf.createEntityManager()
    em.transaction.begin()
    try {
      // get the current value
      var value: Long?
      try {
        value = em.createQuery("select i.v from IdHolderPo i where i.t = :type", Long::class.javaObjectType)
          .setParameter("type", t)
          .singleResult
      } catch (e: NoResultException) {
        value = null
      }

      if (value == null) {  // create one if not exists
        value = 1L
        val id = IdHolderPo(t = t, v = value)
        em.persist(id)
      } else {              // add 1
        value++
        val count = em.createQuery("update IdHolderPo i set i.v = i.v + 1 where i.t = :type")
          .setParameter("type", t).executeUpdate()
        if (count != 1) throw SecurityException("Failed to increase id value for type '$t'")
      }

      em.transaction.commit()
      return value
    } catch (e: Exception) {
      em.transaction.rollback()
      throw e
    } finally {
      em.close()
    }
  }
}