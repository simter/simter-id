package tech.simter.id.impl.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import tech.simter.id.OPERATION_NEXT
import tech.simter.id.core.IdDao
import tech.simter.id.core.IdService
import tech.simter.id.core.IdService.Companion.FORMATTER_SN2
import tech.simter.id.core.IdService.Companion.FORMATTER_SN3
import tech.simter.id.core.IdService.Companion.FORMATTER_SN4
import tech.simter.id.core.IdService.Companion.FORMATTER_SN5
import tech.simter.id.core.IdService.Companion.FORMATTER_yyyy
import tech.simter.id.core.IdService.Companion.FORMATTER_yyyyMM
import tech.simter.id.core.IdService.Companion.FORMATTER_yyyyMMdd
import tech.simter.reactive.security.ModuleAuthorizer
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringJUnitConfig(ModuleConfiguration::class)
@MockkBean(IdDao::class, ModuleAuthorizer::class)
class IdServiceImplTest @Autowired constructor(
  private val moduleAuthorizer: ModuleAuthorizer,
  private val dao: IdDao,
  private val service: IdService
) {
  @Test
  fun nextLong() {
    // mock
    val type = "test"
    val value = 1L
    every { dao.nextLong(type) } returns Mono.just(value)
    every { moduleAuthorizer.verifyHasPermission(OPERATION_NEXT) } returns Mono.empty()

    // invoke and verify
    service.nextLong(type).test().expectNext(value).verifyComplete()
    verify(exactly = 1) {
      moduleAuthorizer.verifyHasPermission(OPERATION_NEXT)
      dao.nextLong(type)
    }
  }

  @Test
  fun nextInt() {
    // mock
    val type = "test"
    val value = 1L
    every { dao.nextLong(type) } returns Mono.just(value)
    every { moduleAuthorizer.verifyHasPermission(OPERATION_NEXT) } returns Mono.empty()

    // invoke and verify
    service.nextInt(type).test().expectNext(value.toInt()).verifyComplete()
    verify(exactly = 1) {
      moduleAuthorizer.verifyHasPermission(OPERATION_NEXT)
      dao.nextLong(type)
    }
  }

  @Test
  fun `nextSN - default`() = testNextSN(FORMATTER_yyyyMMdd, FORMATTER_SN5) { service.nextSN(it) }

  @Test
  fun `nextDailySN2 - default`() = testNextSN(FORMATTER_yyyyMMdd, FORMATTER_SN2) { service.nextDailySN2(it) }

  @Test
  fun `nextDailySN3 - default`() = testNextSN(FORMATTER_yyyyMMdd, FORMATTER_SN3) { service.nextDailySN3(it) }

  @Test
  fun `nextDailySN4 - default`() = testNextSN(FORMATTER_yyyyMMdd, FORMATTER_SN4) { service.nextDailySN4(it) }

  @Test
  fun `nextDailySN5 - default`() = testNextSN(FORMATTER_yyyyMMdd, FORMATTER_SN5) { service.nextDailySN5(it) }

  @Test
  fun `nextMonthlySN2 - default`() = testNextSN(FORMATTER_yyyyMM, FORMATTER_SN2) { service.nextMonthlySN2(it) }

  @Test
  fun `nextMonthlySN3 - default`() = testNextSN(FORMATTER_yyyyMM, FORMATTER_SN3) { service.nextMonthlySN3(it) }

  @Test
  fun `nextMonthlySN4 - default`() = testNextSN(FORMATTER_yyyyMM, FORMATTER_SN4) { service.nextMonthlySN4(it) }

  @Test
  fun `nextMonthlySN5 - default`() = testNextSN(FORMATTER_yyyyMM, FORMATTER_SN5) { service.nextMonthlySN5(it) }

  @Test
  fun `nextYearlySN2 - default`() = testNextSN(FORMATTER_yyyy, FORMATTER_SN2) { service.nextYearlySN2(it) }

  @Test
  fun `nextYearlySN3 - default`() = testNextSN(FORMATTER_yyyy, FORMATTER_SN3) { service.nextYearlySN3(it) }

  @Test
  fun `nextYearlySN4 - default`() = testNextSN(FORMATTER_yyyy, FORMATTER_SN4) { service.nextYearlySN4(it) }

  @Test
  fun `nextYearlySN5 - default`() = testNextSN(FORMATTER_yyyy, FORMATTER_SN5) { service.nextYearlySN5(it) }

  private fun testNextSN(dateTimeFormatter: DateTimeFormatter, snFormatter: NumberFormat, f: (String) -> Mono<String>) {
    // mock
    val date = LocalDate.now().format(dateTimeFormatter)
    val type = "test"
    val value = 1L
    val sn = "$date${snFormatter.format(value)}"
    every { dao.nextLong("$type.$date") } returns Mono.just(value)
    every { moduleAuthorizer.verifyHasPermission(OPERATION_NEXT) } returns Mono.empty()

    // invoke and verify
    f(type).test().expectNext(sn).verifyComplete()
    verify(exactly = 1) {
      moduleAuthorizer.verifyHasPermission(OPERATION_NEXT)
      dao.nextLong("$type.$date")
    }
  }
}