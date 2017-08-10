package tech.simter.id.service;

import tech.simter.id.dao.IdDao;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The ID service implementation.
 *
 * @author RJ
 */
@Named
@Singleton
@Transactional
public class IdServiceImpl implements IdService {
  @Inject
  private IdDao idDao;

  @Override
  public Long next(String type) {
    return idDao.next(type);
  }

  @Override
  public String nextDailySN(String type, DateTimeFormatter dayFormatter, String snPattern) {
    Objects.requireNonNull(type, "type could not be null.");

    // Daily value
    String yyyyMMdd = (null == dayFormatter ? DEFAULT_DAY_FORMATTER : dayFormatter).format(LocalDate.now());

    // Next ID value
    Long nextId = this.next(type + SEPARATOR + yyyyMMdd);

    // Mix
    DecimalFormat idFormatter = null == snPattern ? DEFAULT_DECIMAL_FORMATTER : new DecimalFormat(snPattern);
    return yyyyMMdd + idFormatter.format(nextId);
  }

  @Override
  public String nextDailySN(Class<?> type, DateTimeFormatter dayFormatter, String snPattern) {
    Objects.requireNonNull(type, "type could not be null.");
    return nextDailySN(type.getSimpleName(), dayFormatter, snPattern);
  }
}