package rustamscode.order_ms.entity.order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import rustamscode.order_ms.entity.BaseEntity;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.payload.BaseEvent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "outbox")
public class Outbox extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OutboxStatus status;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
  private BaseEvent payload;

  @Deprecated(forRemoval = true)
  @Column(name = "event_type", nullable = false)
  private String eventType;

  @Column(name = "topic_name", nullable = false)
  private String topicName;
}
