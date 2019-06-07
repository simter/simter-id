package tech.simter.id.impl

import tech.simter.id.core.IdHolder

/**
 * The immutable implementation of [IdHolder].
 *
 * @author RJ
 */
data class ImmutableIdHolder(
  override val t: String,
  override val v: Long
) : IdHolder {
  companion object {
    fun from(idHolder: IdHolder): ImmutableIdHolder {
      return if (idHolder is ImmutableIdHolder) idHolder
      else ImmutableIdHolder(
        t = idHolder.t,
        v = idHolder.v
      )
    }
  }
}