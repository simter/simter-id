package tech.simter.id.service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

/**
 * The ID service.
 *
 * @author RJ
 */
public interface IdService {
  String SEPARATOR = ".";
  /**
   * The default SN (serial number) pattern.
   */
  String DEFAULT_SN_PATTERN = "00000";
  /**
   * The default SN (serial number) DecimalFormat.
   */
  DecimalFormat DEFAULT_DECIMAL_FORMATTER = new DecimalFormat(DEFAULT_SN_PATTERN);

  /**
   * The default day formatter. It is "yyyyMMdd".
   */
  DateTimeFormatter DEFAULT_DAY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

  /**
   * Get the next id for the specific type.
   *
   * @param type the type
   * @return the next id
   */
  Long next(String type);

  /**
   * Get the next daily SN (serial number).
   * <p>
   * The return value format is “yyyyMMdd####”. "####" is control by the snPattern param.
   *
   * @param type         the type
   * @param dayFormatter the day formatter, from {@link DateTimeFormatter}.
   *                     Default is {@link #DEFAULT_DAY_FORMATTER}.
   * @param snPattern    the SN pattern, from {@link DecimalFormat}.
   *                     Default is {@link #DEFAULT_SN_PATTERN}.
   * @return the next daily SN
   */
  String nextDailySN(String type, DateTimeFormatter dayFormatter, String snPattern);

  /**
   * Get the next daily SN (serial number).
   * <p>
   * The return value format is “yyyyMMdd####”. "####" is control by the snPattern param.
   *
   * @param type         the type
   * @param dayFormatter the day formatter, from {@link DateTimeFormatter}.
   *                     Default is {@link #DEFAULT_DAY_FORMATTER}.
   * @param snPattern    the SN pattern, from {@link DecimalFormat}.
   *                     Default is {@link #DEFAULT_SN_PATTERN}.
   * @return the next daily SN
   */
  String nextDailySN(Class<?> type, DateTimeFormatter dayFormatter, String snPattern);
}