package rustamscode.order_ms.entity.payload;

import rustamscode.order_ms.dto.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(UUID id,
                                UUID aggregateId,
                                UUID customerId,
                                BigDecimal totalPrice,
                                List<OrderItemDto> items) implements BaseEvent {
}
