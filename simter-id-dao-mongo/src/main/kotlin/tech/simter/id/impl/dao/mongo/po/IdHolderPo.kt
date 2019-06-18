package tech.simter.id.impl.dao.mongo.po

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import tech.simter.id.TABLE_ID
import tech.simter.id.core.IdHolder

/**
 * The Mongo Document implementation of [IdHolder].
 *
 * @author RJ
 */
@Document(collection = TABLE_ID)
data class IdHolderPo(
  @Id
  override val t: String,
  override val v: Long
) : IdHolder {
  companion object {
    fun from(idHolder: IdHolder): IdHolderPo {
      return if (idHolder is IdHolderPo) idHolder
      else IdHolderPo(
        t = idHolder.t,
        v = idHolder.v
      )
    }
  }
}