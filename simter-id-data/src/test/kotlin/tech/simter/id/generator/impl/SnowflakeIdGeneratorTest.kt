package tech.simter.id.generator.impl

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import tech.simter.id.generator.impl.SnowflakeIdGenerator.SnowflakeId
import tech.simter.id.generator.impl.SnowflakeIdGenerator.machineId
import java.time.OffsetDateTime
import java.util.concurrent.Callable
import java.util.concurrent.Executors


/**
 * Test [SnowflakeIdGenerator].
 *
 * @author RJ
 */
class SnowflakeIdGeneratorTest {
  private val idGenerator = SnowflakeIdGenerator()
  private val executor = Executors.newFixedThreadPool(10)

  @Test
  fun `multiple thread test`() {
    val threadCount = 10 // thread count
    val idCount = 1000   // id count generated in each thread
    val now = OffsetDateTime.now().minusNanos(1000000L)

    // submit all task
    val futures = (1..threadCount).map { executor.submit(task(idCount)) }

    // get all task result
    val ids: MutableList<Triple<Long, OffsetDateTime, Long>> = mutableListOf()
    futures.map { it.get() }.forEach { ids.addAll(it) }
    ids.sortBy { it.first } // sore by id asc

    // verify
    var pre = SnowflakeId(0L, 0, 0, now, -1L)
    ids.forEach {
      assertEquals(idCount * threadCount, ids.size)
      val snowflakeId = idGenerator.parse(it.first)
      assertFalse(snowflakeId.timestamp.isBefore(it.second))
      assertEquals(0, snowflakeId.machineId)
      assertEquals(0, snowflakeId.machineId)

      // verify from 0 and step 1
      if (snowflakeId.timestamp == pre.timestamp)
        assertEquals(snowflakeId.sequence, pre.sequence + 1)
      else
        assertEquals(0, snowflakeId.sequence)

      pre = snowflakeId
    }
  }

  private fun task(count: Int): Callable<List<Triple<Long, OffsetDateTime, Long>>> {
    return Callable {
      (1..count).map {
        // println(Thread.currentThread().id)
        val now = OffsetDateTime.now().minusNanos(1000000L) // - 1 millis second
        Triple(idGenerator.nextId(), now, Thread.currentThread().id)
      }
    }
  }

  @Test
  fun `difference machine id test`() {
    val maxDataCenterId = -1 xor (-1 shl 5)
    val maxWorkId = -1 xor (-1 shl 5)
    for (dataCenterId in (0..maxDataCenterId)) {
      for (workerId in (0..maxWorkId)) {
        val machineId = machineId(dataCenterId, workerId, 5)
        //println(machineId)
        val idGenerator = SnowflakeIdGenerator(machineId)
        val now = OffsetDateTime.now().minusNanos(1000000L) // - 1 millis second
        val id = idGenerator.parse(idGenerator.nextId())
        assertEquals(machineId, id.machineId)
        assertFalse(id.timestamp.isBefore(now))

        //println(idGenerator)
        //println(id)
      }
    }
  }
}