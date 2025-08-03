package rustamscode.order_ms.entity;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements Serializable {

  @Id
  @EqualsAndHashCode.Include
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @Column(name = "id", nullable = false, updatable = false)
  private UUID id;

  @CreationTimestamp
  @Column(name = "created_date", nullable = false, updatable = false)
  private OffsetDateTime createdDate;

  @UpdateTimestamp
  @Column(name = "last_modified_date")
  private OffsetDateTime lastModifiedDate;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @Version
  @Column(name = "version", nullable = false)
  private Long version;
}
