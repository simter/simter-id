package tech.simter.id.generator.impl;

import reactor.core.publisher.Mono;
import tech.simter.id.dao.IdDao;
import tech.simter.id.generator.IdGenerator;

/**
 * An auto increment {@link Long} {@link IdGenerator} base on the specific type.
 * <p>
 * Each type's id is start from 1 and step 1.
 * <p>
 * This {@link IdGenerator} use {@link IdDao#nextLong(String)} to get the next id.
 *
 * @author RJ
 * @see IdDao#nextLong(String)
 */
public class TypeIdGenerator implements IdGenerator<Mono<Long>> {
  private String type;
  private IdDao idDao;

  public TypeIdGenerator(String type, IdDao idDao) {
    this.type = type;
    this.idDao = idDao;
  }

  @Override
  public Mono<Long> nextId() {
    return idDao.nextLong(type);
  }
}