package rustamscode.order_ms.entity.payload;

import java.util.UUID;

public record OrderCancelledEvent(UUID id,
                                  UUID aggregateId,
                                  UUID customerId,
                                  String message) implements BaseEvent{
}
