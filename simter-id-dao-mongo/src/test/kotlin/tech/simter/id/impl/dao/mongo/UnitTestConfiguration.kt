package tech.simter.id.impl.dao.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * @author RJ
 */
@Configuration
@Import(
  //tech.simter.mongo.ModuleConfiguration::class,
  ModuleConfiguration::class
)
class UnitTestConfiguration