package tech.simter.id.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * ID holder.
 *
 * @author RJ
 */
@Entity
public class IdHolder implements Serializable {
  /**
   * The type.
   */
  @Id
  @Column(nullable = false, length = 100)
  public String type;

  /**
   * The current value.
   */
  @Column(nullable = false)
  public Long value;

  public IdHolder() {
  }

  public IdHolder(String type, Long value) {
    this.type = type;
    this.value = value;
  }
}