package tech.simter.id.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The ID holder interface.
 *
 * @author RJ
 */
interface IdHolder {
  /** The type */
  val t: String

  /** The current value */
  val v: Long

  companion object {
    /** An inner immutable [IdHolder] implementation */
    @Serializable
    @SerialName("IdHolder")
    internal data class Impl(
      override val t: String,
      override val v: Long
    ) : IdHolder

    /** Create an immutable [IdHolder] instance */
    fun of(t: String, v: Long): IdHolder {
      return Impl(t = t, v = v)
    }
  }
}