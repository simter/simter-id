package tech.simter.id.impl.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import tech.simter.id.AUTHORIZER_KEY
import tech.simter.id.PACKAGE
import tech.simter.reactive.security.ModuleAuthorizer
import tech.simter.reactive.security.ReactiveSecurityService
import tech.simter.reactive.security.properties.ModuleAuthorizeProperties
import tech.simter.reactive.security.properties.PermissionStrategy.Deny

/**
 * All configuration for this module.
 *
 * @author RJ
 */
@Configuration("$PACKAGE.impl.service.ModuleConfiguration")
@EnableConfigurationProperties
@ComponentScan
class ModuleConfiguration {
  /**
   * Starter should config yml key [AUTHORIZER_KEY] to support specific role control,
   * otherwise the [ModuleConfiguration.moduleAuthorizer] would deny anything default.
   */
  @Bean("$AUTHORIZER_KEY.properties")
  @ConfigurationProperties(prefix = AUTHORIZER_KEY)
  fun moduleAuthorizeProperties(): ModuleAuthorizeProperties {
    return ModuleAuthorizeProperties(defaultPermission = Deny)
  }

  @Bean("$AUTHORIZER_KEY.authorizer")
  fun moduleAuthorizer(
    @Qualifier("$AUTHORIZER_KEY.properties")
    properties: ModuleAuthorizeProperties,
    securityService: ReactiveSecurityService
  ): ModuleAuthorizer {
    return ModuleAuthorizer.create(properties, securityService)
  }
}