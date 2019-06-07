package tech.simter.id.dao.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tech.simter.id.po.IdHolder
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

/**
 * The JPA implementation of [IdBlockDao].
 *
 * @author RJ
 */
@Component
internal class IdBlockDaoImpl @Autowired constructor(
  @PersistenceContext private val em: EntityManager
) : IdBlockDao {
  @Synchronized
  @Transactional
  override fun nextLong(t: String): Long {
    // get the current value
    var value: Long?
    try {
      value = em.createQuery("select i.v from IdHolder i where i.t = :type", Long::class.javaObjectType)
        .setParameter("type", t)
        .singleResult
    } catch (e: NoResultException) {
      value = null
    }

    if (value == null) {  // create one if not exists
      value = 1L
      val id = IdHolder(t = t, v = value)
      em.persist(id)
    } else {              // add 1
      value++
      val count = em.createQuery("update IdHolder i set i.v = i.v + 1 where i.t = :type")
        .setParameter("type", t).executeUpdate()
      if (count != 1) throw SecurityException("Failed to increase id value for type '$t'")
    }

    return value
  }
}