package tech.simter.id.service

import reactor.core.publisher.Mono
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * The ID service.
 *
 * @author RJ
 */
interface IdService {
  /**
   * Get the next id for the specific type.
   *
   * @param[t] the type
   * @return the next id
   */
  fun nextLong(t: String): Mono<Long>

  /**
   * Get the next id for the specific type.
   *
   * @param[t] the type
   * @return the next id
   */
  fun nextInt(t: String): Mono<Int> {
    return nextLong(t).map { it.toInt() }
  }

  /**
   * Get the next date time base serial number.
   *
   * The return value format is `$Type.$dateTime$SN`.
   * 1. `$Type` is the value of param [t].
   * 2. `$SN` is controlled by param [snFormatter].
   * 3. `$dateTime$SN` is controlled by param [dateTimeFormatter].
   *
   * @param[t]                 the type
   * @param[snFormatter]       the serial number formatter, default is [FORMATTER_SN5]
   * @param[dateTimeFormatter] the [DateTimeFormatter] for format current datetime, default is [FORMATTER_yyyyMMdd]
   * @return the next SN
   */
  fun nextSN(t: String, snFormatter: NumberFormat = FORMATTER_SN5,
             dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMMdd): Mono<String> {
    // current date time string
    val date = LocalDateTime.now().format(dateTimeFormatter)

    // return
    return nextLong("$t$SEPARATOR$date")  // next id
      .map { date + snFormatter.format(it) } // date + sn
  }

  /** Get the next daily serial number with format `yyyyMMdd##` */
  fun nextDailySN2(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMMdd): Mono<String> {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter)
  }

  /** Get the next daily serial number with format `yyyyMMdd###` */
  fun nextDailySN3(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMMdd): Mono<String> {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter)
  }

  /** Get the next daily serial number with format `yyyyMMdd####` */
  fun nextDailySN4(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMMdd): Mono<String> {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter)
  }

  /** Get the next daily serial number with format `yyyyMMdd#####` */
  fun nextDailySN5(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMMdd): Mono<String> {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter)
  }

  /** Get the next monthly serial number with format `yyyyMM##` */
  fun nextMonthlySN2(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMM): Mono<String> {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter)
  }

  /** Get the next monthly serial number with format `yyyyMM###` */
  fun nextMonthlySN3(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMM): Mono<String> {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter)
  }

  /** Get the next monthly serial number with format `yyyyMM####` */
  fun nextMonthlySN4(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMM): Mono<String> {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter)
  }

  /** Get the next monthly serial number with format `yyyyMM#####` */
  fun nextMonthlySN5(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyyMM): Mono<String> {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter)
  }

  /** Get the next yearly serial number with format `yyyy##` */
  fun nextYearlySN2(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyy): Mono<String> {
    return nextSN(t, FORMATTER_SN2, dateTimeFormatter)
  }

  /** Get the next yearly serial number with format `yyyy###` */
  fun nextYearlySN3(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyy): Mono<String> {
    return nextSN(t, FORMATTER_SN3, dateTimeFormatter)
  }

  /** Get the next yearly serial number with format `yyyy####` */
  fun nextYearlySN4(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyy): Mono<String> {
    return nextSN(t, FORMATTER_SN4, dateTimeFormatter)
  }

  /** Get the next yearly serial number with format `yyyy#####` */
  fun nextYearlySN5(t: String, dateTimeFormatter: DateTimeFormatter = FORMATTER_yyyy): Mono<String> {
    return nextSN(t, FORMATTER_SN5, dateTimeFormatter)
  }

  companion object {
    /** A dot separator */
    const val SEPARATOR = "."
    /** Two-digit serial number formatter */
    val FORMATTER_SN2 = DecimalFormat("00")
    /** Three-digit serial number formatter */
    val FORMATTER_SN3 = DecimalFormat("000")
    /** Four-digit serial number formatter */
    val FORMATTER_SN4 = DecimalFormat("0000")
    /** Five-digit serial number formatter */
    val FORMATTER_SN5 = DecimalFormat("00000")
    /** A date formatter with pattern "yyyyMMdd" */
    val FORMATTER_yyyyMMdd: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    /** A month formatter with pattern "yyyyMM" */
    val FORMATTER_yyyyMM: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMM")
    /** A year formatter with pattern "yyyy" */
    val FORMATTER_yyyy: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy")
  }
}