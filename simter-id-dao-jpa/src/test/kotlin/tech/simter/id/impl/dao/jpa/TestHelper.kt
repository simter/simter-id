package tech.simter.id.impl.dao.jpa

import tech.simter.id.impl.dao.jpa.po.IdHolderPo
import tech.simter.util.RandomUtils.randomInt
import tech.simter.util.RandomUtils.randomString

object TestHelper {
  fun randomIdHolder(): IdHolderPo = IdHolderPo("t-" + randomString(), randomInt(1, 100).toLong())
}