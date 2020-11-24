package tech.simter.id.core

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

  /** An inner immutable [IdHolder] implementation */
  private data class Impl(
    override val t: String,
    override val v: Long
  ) : IdHolder

  companion object {
    /** Create an immutable [IdHolder] instance */
    fun of(t: String, v: Long): IdHolder {
      return Impl(t = t, v = v)
    }
  }
}