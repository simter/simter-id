package tech.simter.id.dao;

import tech.simter.id.po.IdHolder;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * The ID Dao implementation.
 *
 * @author RJ
 */
@Named
@Singleton
public class IdDaoJpaImpl implements IdDao {
  @PersistenceContext
  private EntityManager em;

  @Override
  public Long next(String type) {
    // get the current value
    Long value;
    try {
      value = em.createQuery("select value from IdHolder where type = :type", Long.class)
        .setParameter("type", type)
        .getSingleResult();
    } catch (NoResultException e) {
      value = null;
    }

    if (value == null) {  // create one if not exists
      value = 1L;
      IdHolder id = new IdHolder(type, value);
      em.persist(id);
    } else {              // add 1
      value++;
      int count = em.createQuery("update IdHolder set value = value + 1 where type = :type")
        .setParameter("type", type).executeUpdate();
      if (count != 1) throw new SecurityException("Failed to increase id value for type '" + type + "'");
    }

    return value;
  }
}