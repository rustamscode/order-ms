package rustamscode.order_ms.entity.payload;

import java.math.BigDecimal;
import java.util.UUID;


public record OrderCancelledPayload(UUID id, UUID customerId, BigDecimal totalPrice, String message) {
}
