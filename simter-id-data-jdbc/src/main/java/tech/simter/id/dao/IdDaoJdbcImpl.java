package tech.simter.id.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * The ID Dao implementation.
 *
 * @author RJ
 */
@Named
@Singleton
public class IdDaoJdbcImpl implements IdDao {
  @Inject
  private JdbcTemplate jdbcTemplate;

  @Override
  public Long next(String type) {
    // get the current value
    Long value;
    try {
      value = jdbcTemplate.queryForObject("select value from id_holder where type = ?",
        new Object[]{type}, Long.class);
    } catch (EmptyResultDataAccessException e) {
      value = null;
    }

    if (value == null) {  // create one if not exists
      value = 1L;
      int count = jdbcTemplate.update("insert into id_holder(type, value) values(?, ?)", type, value);
      if (count != 1) throw new SecurityException("Failed to insert id_holder for type '" + type + "'");
    } else {              // add 1
      value++;
      int count = jdbcTemplate.update("update id_holder set value = value + 1 where type = ?", type);
      if (count != 1) throw new SecurityException("Failed to increase id value for type '" + type + "'");
    }

    return value;
  }
}