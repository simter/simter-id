package tech.simter.id.service;

import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The ID service.
 *
 * @author RJ
 */
public interface IdService {
  /**
   * Get the next id for the specific type.
   *
   * @param t the type
   * @return the next id
   */
  Mono<Long> nextLong(String t);

  /**
   * Get the next id for the specific type.
   *
   * @param t the type
   * @return the next id
   */
  default Mono<Integer> nextInt(String t) {
    return nextLong(t).map(Long::intValue);
  }

  /**
   * Get the next date time base serial number.
   * <p>
   * The return value format is `$Type.$dateTime$SN`.
   * 1. `$Type` is the value of param t.
   * 2. `$SN` is controlled by param snFormatter.
   * 3. `$dateTime$SN` is controlled by param dateTimeFormatter.
   *
   * @param t                 the type
   * @param snFormatter       the serial number formatter, default is {@link #FORMATTER_SN5}
   * @param dateTimeFormatter the {@link DateTimeFormatter} for format current datetime, default is {@link #FORMATTER_yyyyMMdd}
   * @return the next SN
   */
  default Mono<String> nextSN(String t, NumberFormat snFormatter, DateTimeFormatter dateTimeFormatter) {
    // current date time string
    String date = LocalDateTime.now().format(dateTimeFormatter);

    // return
    return nextLong(t + SEPARATOR + date)     // next id
      .map(it -> date + snFormatter.format(it)); // date + sn
  }

  /**
   * Get the next daily 2 bit serial number with date format `yyyyMMdd##`
   */
  default Mono<String> nextDailySN2(String t) {
    return nextSN(t, FORMATTER_SN2, FORMATTER_yyyyMMdd);
  }

  /**
   * Get the next daily 2 bit serial number with specific date formatter
   */
  default Mono<String> nextDailySN2(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter);
  }

  /**
   * Get the next daily 3 bit serial number with format `yyyyMMdd###`
   */
  default Mono<String> nextDailySN3(String t) {
    return nextSN(t, FORMATTER_SN3, FORMATTER_yyyyMMdd);
  }

  /**
   * Get the next daily 3 bit serial number with specific date formatter
   */
  default Mono<String> nextDailySN3(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter);
  }

  /**
   * Get the next daily 4 bit serial serial number with format `yyyyMMdd####`
   */
  default Mono<String> nextDailySN4(String t) {
    return nextSN(t, FORMATTER_SN4, FORMATTER_yyyyMMdd);
  }

  /**
   * Get the next daily 4 bit serial serial number with specific date formatter
   */
  default Mono<String> nextDailySN4(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter);
  }

  /**
   * Get the next daily 5 bit serial serial number with format `yyyyMMdd#####`
   */
  default Mono<String> nextDailySN5(String t) {
    return nextSN(t, FORMATTER_SN5, FORMATTER_yyyyMMdd);
  }

  /**
   * Get the next daily 5 bit serial serial number with specific date formatter
   */
  default Mono<String> nextDailySN5(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter);
  }

  /**
   * Get the next monthly 2 bit serial serial number with format `yyyyMM##`
   */
  default Mono<String> nextMonthlySN2(String t) {
    return nextSN(t, FORMATTER_SN2, FORMATTER_yyyyMM);
  }

  /**
   * Get the next monthly 2 bit serial serial number with specific month formatter
   */
  default Mono<String> nextMonthlySN2(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter);
  }

  /**
   * Get the next monthly 3 bit serial serial number with format `yyyyMM###`
   */
  default Mono<String> nextMonthlySN3(String t) {
    return nextSN(t, FORMATTER_SN3, FORMATTER_yyyyMM);
  }

  /**
   * Get the next monthly 3 bit serial serial number with specific month formatter
   */
  default Mono<String> nextMonthlySN3(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter);
  }

  /**
   * Get the next monthly 4 bit serial serial number with format `yyyyMM####`
   */
  default Mono<String> nextMonthlySN4(String t) {
    return nextSN(t, FORMATTER_SN4, FORMATTER_yyyyMM);
  }

  /**
   * Get the next monthly 4 bit serial serial number with specific month formatter
   */
  default Mono<String> nextMonthlySN4(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter);
  }

  /**
   * Get the next monthly 5 bit serial serial number with format `yyyyMM#####`
   */
  default Mono<String> nextMonthlySN5(String t) {
    return nextSN(t, FORMATTER_SN5, FORMATTER_yyyyMM);
  }

  /**
   * Get the next monthly 5 bit serial serial number with specific month formatter
   */
  default Mono<String> nextMonthlySN5(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter);
  }

  /**
   * Get the next yearly 2 bit serial serial number with format `yyyy##`
   */
  default Mono<String> nextYearlySN2(String t) {
    return nextSN(t, FORMATTER_SN2, FORMATTER_yyyy);
  }

  /**
   * Get the next yearly 2 bit serial serial number with specific year formatter
   */
  default Mono<String> nextYearlySN2(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter);
  }

  /**
   * Get the next yearly 3 bit serial serial number with format `yyyy###`
   */
  default Mono<String> nextYearlySN3(String t) {
    return nextSN(t, FORMATTER_SN3, FORMATTER_yyyy);
  }

  /**
   * Get the next yearly 3 bit serial serial number with specific year formatter
   */
  default Mono<String> nextYearlySN3(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter);
  }

  /**
   * Get the next yearly 4 bit serial serial number with format `yyyy####`
   */
  default Mono<String> nextYearlySN4(String t) {
    return nextSN(t, FORMATTER_SN4, FORMATTER_yyyy);
  }

  /**
   * Get the next yearly 4 bit serial serial number with specific year formatter
   */
  default Mono<String> nextYearlySN4(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter);
  }

  /**
   * Get the next yearly 5 bit serial serial number with format `yyyy#####`
   */
  default Mono<String> nextYearlySN5(String t) {
    return nextSN(t, FORMATTER_SN5, FORMATTER_yyyy);
  }

  /**
   * Get the next yearly 5 bit serial serial number with specific year formatter
   */
  default Mono<String> nextYearlySN5(String t, DateTimeFormatter dateTimeFormatter) {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter);
  }

  /**
   * A dot separator
   */
  String SEPARATOR = ".";
  /**
   * Two-digit serial number formatter
   */
  DecimalFormat FORMATTER_SN2 = new DecimalFormat("00");
  /**
   * Three-digit serial number formatter
   */
  DecimalFormat FORMATTER_SN3 = new DecimalFormat("000");
  /**
   * Four-digit serial number formatter
   */
  DecimalFormat FORMATTER_SN4 = new DecimalFormat("0000");
  /**
   * Five-digit serial number formatter
   */
  DecimalFormat FORMATTER_SN5 = new DecimalFormat("00000");
  /**
   * A date formatter with pattern "yyyyMMdd"
   */
  DateTimeFormatter FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
  /**
   * A month formatter with pattern "yyyyMM"
   */
  DateTimeFormatter FORMATTER_yyyyMM = DateTimeFormatter.ofPattern("yyyyMM");
  /**
   * A year formatter with pattern "yyyy"
   */
  DateTimeFormatter FORMATTER_yyyy = DateTimeFormatter.ofPattern("yyyy");
}