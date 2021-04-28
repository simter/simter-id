package tech.simter.id

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tech.simter.id.core.IdHolder

/**
 * All configuration for this module.
 *
 * @author RJ
 */
@Configuration("$PACKAGE.core")
class ModuleConfiguration {
  @Bean
  fun serializersModule4IdHolder(): SerializersModule {
    return SerializersModule {
      polymorphic(IdHolder::class) {
        subclass(IdHolder.Companion.Impl::class)
      }

      // for Page<T>
      // https://github.com/simter/simter-kotlin/blob/master/src/test/kotlin/tech/simter/kotlin/serialization/PageTest.kt
      // https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/polymorphism.md#polymorphism-and-generic-classes
      polymorphic(Any::class) {
        subclass(IdHolder.Companion.Impl::class)
      }
    }
  }
}