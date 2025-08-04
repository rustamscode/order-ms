package rustamscode.order_ms.dto;

import lombok.Builder;
import rustamscode.order_ms.entity.payload.OutboxPayload;

import java.util.UUID;

@Builder
public record OutboxCreateRq(UUID aggregateId, OutboxPayload payload, String eventType, String topicName) {
}
