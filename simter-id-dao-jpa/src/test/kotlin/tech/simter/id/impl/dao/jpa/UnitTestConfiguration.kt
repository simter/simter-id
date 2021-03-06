package tech.simter.id.impl.dao.jpa

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * @author RJ
 */
@Configuration
@Import(
  tech.simter.reactive.jpa.ModuleConfiguration::class,
  tech.simter.reactive.test.ModuleConfiguration::class,
  tech.simter.id.impl.dao.jpa.ModuleConfiguration::class
)
class UnitTestConfiguration