package rustamscode.order_ms.dto;

import lombok.Builder;
import rustamscode.order_ms.entity.payload.BaseEvent;

import java.util.UUID;

@Builder
public record OutboxCreateRq(BaseEvent payload, String eventType, String topicName) {
}
