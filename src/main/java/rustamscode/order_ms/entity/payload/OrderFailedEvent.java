package rustamscode.order_ms.entity.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderFailedEvent implements BaseEvent {
  private UUID id;
  private UUID aggregateId;
  private UUID customerId;
  private String message;
}
