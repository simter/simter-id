package tech.simter.id.test

import tech.simter.id.core.IdHolder
import tech.simter.util.RandomUtils.randomLong
import tech.simter.util.RandomUtils.randomString

/**
 * Some common tools for unit test.
 *
 * @author RJ
 */
object TestHelper {
  /** Create a random id type */
  fun randomIdType(len: Int = 6): String {
    return randomString(len = len)
  }

  /** Create a random id value */
  fun randomIdValue(): Long {
    return randomLong()
  }

  /** Create a random [IdHolder] instance */
  fun randomIdHolder(
    t: String = randomIdType(),
    v: Long = randomIdValue()
  ): IdHolder {
    return IdHolder.of(t = t, v = v)
  }
}