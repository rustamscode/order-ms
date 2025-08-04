package rustamscode.order_ms.entity.order;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import rustamscode.order_ms.entity.BaseEntity;
import rustamscode.order_ms.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

  @Column(name = "total_price", nullable = false)
  private BigDecimal totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  @Column(name = "customer_id", nullable = false)
  private UUID customerId;

  @ToString.Exclude
  @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItem> items = new ArrayList<>();

  public void addItem(OrderItem item) {
    this.items.add(item);
    item.setOrder(this);
  }

  public void removeItem(OrderItem item) {
    this.items.remove(item);
    this.totalPrice = this.totalPrice.subtract(item.getPricePerUnit().multiply(item.getQuantity()));
    item.setOrder(null);
  }
}