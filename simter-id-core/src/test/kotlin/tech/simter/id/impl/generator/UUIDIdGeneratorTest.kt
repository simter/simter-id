package tech.simter.id.impl.generator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.Callable
import java.util.concurrent.Executors

/**
 * Test [UUIDIdGenerator].
 *
 * @author RJ
 */
class UUIDIdGeneratorTest {
  private val idGenerator = UUIDIdGenerator()
  private val executor = Executors.newFixedThreadPool(10)
  private val threadCount = 10 // thread count
  private val idCount = 1000   // id count generated in each thread

  @Test
  fun `multiple thread test`() {
    // submit all task
    val futures = (1..threadCount).map { executor.submit(task(idCount)) }

    // get all task result
    val idList: MutableList<String> = mutableListOf()
    val idSet: MutableSet<String> = mutableSetOf()
    futures.map { it.get() }.forEach { idList.addAll(it);idSet.addAll(it) }

    // verify all id is difference
    assertEquals(idList.size, idSet.size)
  }

  private fun task(count: Int): Callable<List<String>> {
    return Callable { (1..count).map { idGenerator.nextId().toString() } }
  }
}