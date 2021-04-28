package tech.simter.id.impl.dao.jpa.po

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import tech.simter.id.TABLE_ID
import tech.simter.id.core.IdHolder
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * The JPA Entity implementation of [IdHolder].
 *
 * @author RJ
 */
@Entity
@Table(name = TABLE_ID)
@Serializable
@SerialName("IdHolderPo")
data class IdHolderPo(
  @Id
  @Column(length = 100)
  override val t: String,
  override val v: Long
) : IdHolder