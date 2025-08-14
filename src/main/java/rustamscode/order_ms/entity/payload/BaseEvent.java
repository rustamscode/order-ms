package rustamscode.order_ms.entity.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "eventType"
)

@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderCreatedEvent.class, name = "OrderCreated"),
    @JsonSubTypes.Type(value = OrderCancelledEvent.class, name = "OrderCancelled"),
    @JsonSubTypes.Type(value = OrderCompletedEvent.class, name = "OrderCompletedEvent"),
    @JsonSubTypes.Type(value = PaymentCompletedEvent.class, name = "PaymentCompleted"),
    @JsonSubTypes.Type(value = PaymentFailedEvent.class, name = "PaymentFailed")
})
public interface BaseEvent {
}
