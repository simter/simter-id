package tech.simter.id.generator.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import tech.simter.id.generator.IdGenerator;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * A {@link Long} {@link IdGenerator} follow time increment rule by twitter snowflake.
 * <p>
 * It's thread-safe for distribute system.
 * <p>
 * Default id structure:
 * <pre>
 * 0 0...[41bit]...0 0...[10bit]...0 0...[12bit]...0
 * - --------------- --------------- ---------------
 * 0|   timestamp   |   machineId   |   sequence
 *
 * total 64bit positive long data
 * </pre>
 * <p>
 * <ol>
 * <li>timestamp - millis second after [startTime].
 * <li>machineId - from 0 to 1023 (2^10 -1).
 * <li>sequence - sequence number within the same milli second, start from 0 and step by 1, from 0 to 4095 (2^12 -1).
 * </ol>
 * Ref <a href="https://juejin.im/entry/59c4add2f265da066a103db4">here</a>.
 *
 * @author RJ
 */
public class SnowflakeIdGenerator implements IdGenerator<Long> {
  public static final int DEFAULT_MACHINE_ID_BITS = 10;
  public static final int DEFAULT_SEQUENCE_BITS = 12;
  public static final OffsetDateTime DEFAULT_START_TIME = OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

  @Getter
  private int machineId;
  @Getter
  private int machineIdBits;
  @Getter
  private int sequenceBits;
  @Getter
  private OffsetDateTime startTime;
  @Getter
  private long startEpoch;

  private final int maxSequenceId;      // 4095=2^12-1
  private final int timestampLeftShift; // 41=64-1-10-12
  private final int maxMachineId;       // 1023=2^10-1
  private long lastTimestamp;
  private int sequence = 0;

  public SnowflakeIdGenerator() {
    this(0);
  }

  public SnowflakeIdGenerator(int machineId) {
    this(machineId, DEFAULT_MACHINE_ID_BITS, DEFAULT_SEQUENCE_BITS, DEFAULT_START_TIME);
  }

  public SnowflakeIdGenerator(
    int machineId,
    int machineIdBits,
    int sequenceBits,
    OffsetDateTime startTime
  ) {
    this.machineId = machineId;
    this.machineIdBits = machineIdBits;
    this.sequenceBits = sequenceBits;
    this.startTime = startTime;

    // computed fields
    this.startEpoch = startTime.toInstant().toEpochMilli();
    this.timestampLeftShift = machineIdBits + sequenceBits;
    this.maxMachineId = ~(-1 << machineIdBits);
    this.maxSequenceId = ~(-1 << sequenceBits);
  }

  // it's really important to make this method synchronized for concurrence system
  @Override
  public synchronized Long nextId() {
    long timestamp = timeGen();

    // throw error if clock moved backwards
    if (timestamp < lastTimestamp) {
      throw new RuntimeException(String.format(
        "Clock moved backwards, refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
    }

    // verify sequence number
    if (lastTimestamp == timestamp) { //  block to get next millis second if sequence overflow
      sequence = (sequence + 1) & maxSequenceId;
      if (sequence == 0) timestamp = tilNextMillis(lastTimestamp);
    } else sequence = 0; // reset sequence number when next millis second

    // save the valid timestamp
    lastTimestamp = timestamp;

    // combine to a long id
    return (timestamp - startEpoch << timestampLeftShift
      | (machineId << sequenceBits)
      | sequence);
  }

  /**
   * get the next current millis second
   */
  private long tilNextMillis(Long lastTimestamp) {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }

  /**
   * get current time
   */
  private long timeGen() {
    return System.currentTimeMillis();
  }

  /**
   * parse long id to a SnowflakeId
   */
  public SnowflakeId parse(long id) {
    long ts = (id >> timestampLeftShift) + startEpoch;
    return new SnowflakeId(
      id,
      (int) ((id >> sequenceBits) & maxMachineId),
      (int) (id & maxSequenceId),
      OffsetDateTime.ofInstant(Instant.ofEpochMilli(ts), ZoneId.systemDefault()),
      ts
    );
  }

  /**
   * Generate a machine id base on specific dataCenterId and workerId.
   *
   * @param dataCenterId the data center id
   * @param workerId     the worker id
   * @param workerIdBits the worker id bits
   * @return the machine id
   */
  public static int machineId(int dataCenterId, int workerId, int workerIdBits) {
    return (dataCenterId << workerIdBits) | workerId;
  }

  /**
   * A snowflake id holder.
   */
  @Data
  @Getter
  @AllArgsConstructor
  public static class SnowflakeId {
    private long value;
    private int machineId;
    private int sequence;
    @NonNull
    private OffsetDateTime timestamp;
    private long epochMilli;
  }
}