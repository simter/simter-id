package tech.simter.id.dao.jpa

import tech.simter.id.po.IdHolder
import tech.simter.util.RandomUtils.randomInt
import tech.simter.util.RandomUtils.randomString

object TestHelper {
  fun randomIdHolder(): IdHolder = IdHolder("t-" + randomString(), randomInt(1, 100).toLong())
}