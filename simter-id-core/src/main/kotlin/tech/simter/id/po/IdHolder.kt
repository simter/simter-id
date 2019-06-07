package tech.simter.id.po

import tech.simter.id.TABLE_ID
import javax.persistence.Column

/**
 * The ID holder.
 *
 * @author RJ
 */
// for jpa
@javax.persistence.Entity
@javax.persistence.Table(name = TABLE_ID)
// for jdbc or r2dbc
@org.springframework.data.relational.core.mapping.Table(TABLE_ID)
// for mongodb
@org.springframework.data.mongodb.core.mapping.Document(collection = TABLE_ID)
data class IdHolder(
  /** The type */
  @javax.persistence.Id
  @org.springframework.data.annotation.Id
  @Column(length = 100)
  val t: String,
  /** The current value */
  val v: Long
)