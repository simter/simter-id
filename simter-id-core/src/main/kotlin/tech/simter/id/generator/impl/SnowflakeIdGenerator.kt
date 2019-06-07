package tech.simter.id.generator.impl

import tech.simter.id.IdGenerator
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset

/**
 * A Snowflake id generator.
 *
 * Default id structure:
 *
 * ```
 * 0 0...[41bit]...0 0...[5bit]...0 0...[5bit]...0 0...[12bit]...0
 * - --------------- -------------- -------------- ---------------
 * 0|   timestamp   | dataCenterId |   workerId   |   sequence
 *
 * total 64bit positive long data
 * ```
 *
 * 1. timestamp - millis second after [startTime].
 * 2. dataCenterId - from 0 to 31.
 * 3. workerId - from 0 to 31.
 * 4. sequence - sequence number within the same milli second, start from 0 and step by 1, from 0 to 4095.
 *
 * Ref [here](https://juejin.im/entry/59c4add2f265da066a103db4)
 * @author RJ
 */
class SnowflakeIdGenerator(
  val dataCenterId: Int = 0,
  val workerId: Int = 0,
  val dataCenterIdBits: Int = 5,
  val workerIdBits: Int = 5,
  val sequenceBits: Int = 12,
  /** start time from 2000-01-01T00:00:00+00:00 */
  val startTime: OffsetDateTime
  //= OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
  = OffsetDateTime.of(2019, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC)
  //= OffsetDateTime.now()
) : IdGenerator<Long> {
  constructor(machineId: Int) : this(
    dataCenterId = (machineId shr 5) and (-1 xor (-1 shl 5)),
    workerId = machineId and (-1 xor (-1 shl 5))
  )

  private val wid = workerId.toLong()
  private val dcId = dataCenterId.toLong()
  private val maxMachineId = -1L xor (-1L shl dataCenterIdBits + workerIdBits)
  private val maxWorkerId = -1L xor (-1L shl workerIdBits)
  private val maxDataCenterId = -1L xor (-1L shl dataCenterIdBits)

  init {
    // verify initial data
    if (wid > maxWorkerId) throw IllegalArgumentException(
      String.format("dataCenter Id can't be greater than %d or less than 0", maxWorkerId))
    if (dcId > maxDataCenterId) throw IllegalArgumentException(
      String.format("dataCenter Id can't be greater than %d or less than 0", maxDataCenterId))
  }

  /** machine id. default 0 */
  val machineId: Int = (dataCenterId shl workerIdBits) or workerId
  val startEpoch = startTime.toInstant().toEpochMilli()
  /** timestamp shift bits. default 22=(5+5+12) */
  private val timestampLeftShift = dataCenterIdBits + workerIdBits + sequenceBits
  /** sequence mask, 4095 (0b111111111111=0xfff=4095) */
  private val sequenceMask = -1L xor (-1L shl sequenceBits)
  /** default 17=5+12 */
  private val dataCenterIdShift = workerIdBits + sequenceBits
  /** default 12 */
  private val workerIdShift = sequenceBits

  private var lastTimestamp = -1L
  /** sequence number in the same millis second, from 0 to 4095(=2^12-1) */
  private var sequence = 0L

  @Synchronized
  override fun nextId(): Long {
    var timestamp = timeGen()

    // throw error if clock moved backwards
    if (timestamp < lastTimestamp) {
      throw RuntimeException(String.format(
        "Clock moved backwards, refusing to generate id for %d milliseconds", lastTimestamp - timestamp))
    }

    // verify sequence number
    if (lastTimestamp == timestamp) { //  block to get next millis second if sequence overflow
      sequence = sequence + 1 and sequenceMask
      if (sequence == 0L) timestamp = tilNextMillis(lastTimestamp)
    } else sequence = 0L // reset sequence number when next millis second

    // save the valid timestamp
    lastTimestamp = timestamp

    // combine to a long id
    return (timestamp - startEpoch shl timestampLeftShift
      or (dcId shl dataCenterIdShift)
      or (wid shl workerIdShift)
      or sequence)
  }

  /** get the next current millis second */
  private fun tilNextMillis(lastTimestamp: Long): Long {
    var timestamp = timeGen()
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen()
    }
    return timestamp
  }

  /** get current time */
  private fun timeGen(): Long {
    return System.currentTimeMillis()
  }

  override fun toString(): String {
    return """SnowflakeIdGenerator(
      |      machineId=$machineId (dataCenterId=$dcId, workerId=$wid),
      |  machineIdBits=${dataCenterIdBits + workerIdBits} (dataCenterIdBits=$dataCenterIdBits, workerIdBits=$workerIdBits),
      |   sequenceBits=$sequenceBits,
      |      startTime=$startTime (startEpoch=$startEpoch)
      |)""".trimMargin()
  }

  /* parse long id to a TimeIncrementId */
  fun parse(id: Long): SnowFlakeId {
    val ts = (id shr timestampLeftShift) + startEpoch
    return SnowFlakeId(
      value = id,
      timeEpoch = ts,
      timestamp = OffsetDateTime.ofInstant(Instant.ofEpochMilli(ts), ZoneId.systemDefault()),
      machineId = ((id shr workerIdShift) and maxMachineId).toInt(),
      dataCenterId = ((id shr dataCenterIdShift) and maxDataCenterId).toInt(),
      workerId = ((id shr workerIdShift) and maxWorkerId).toInt(),
      sequence = (id and sequenceMask).toInt()
    )
  }
}

/** A TimeIncrementId */
data class SnowFlakeId(
  val value: Long,
  val timestamp: OffsetDateTime,
  val timeEpoch: Long,
  val machineId: Int,
  val dataCenterId: Int,
  val workerId: Int,
  val sequence: Int
)