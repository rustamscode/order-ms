package rustamscode.order_ms.entity.payload;

import lombok.Data;
import rustamscode.order_ms.dto.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class OrderCompletedEvent implements BaseEvent {
  private UUID id;
  private UUID aggregateId;
  private UUID customerId;
  private BigDecimal totalPrice;
  private List<OrderItemDto> items;
}
