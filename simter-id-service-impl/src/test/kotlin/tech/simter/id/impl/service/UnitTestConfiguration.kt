package tech.simter.id.impl.service

import com.ninjasquad.springmockk.MockkBean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import tech.simter.id.core.IdDao
import tech.simter.reactive.security.ModuleAuthorizer
import tech.simter.reactive.security.ReactiveSecurityService

/**
 * All test configuration for this module.
 *
 * @author RJ
 */
@Configuration
@Import(ModuleConfiguration::class)
@MockkBean(IdDao::class, ModuleAuthorizer::class, ReactiveSecurityService::class)
class UnitTestConfiguration