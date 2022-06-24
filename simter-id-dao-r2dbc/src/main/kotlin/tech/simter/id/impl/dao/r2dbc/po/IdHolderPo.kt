package tech.simter.id.impl.dao.r2dbc.po

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table
import tech.simter.id.TABLE_ID
import tech.simter.id.core.IdHolder

@Table(TABLE_ID)
@Serializable
@SerialName("IdHolderPo")
data class IdHolderPo(
  @Id
  override val t: String,
  override val v: Long
) : IdHolder, Persistable<String> {
  override fun getId(): String {
    return this.t
  }

  override fun isNew(): Boolean {
    return true
  }
}