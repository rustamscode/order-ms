package rustamscode.order_ms.entity.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.UUID;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "eventType")

@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderCreatedEvent.class, name = "OrderCreatedEvent"),
    @JsonSubTypes.Type(value = OrderCompletedEvent.class, name = "OrderCompletedEvent"),
    @JsonSubTypes.Type(value = PaymentCompletedEvent.class, name = "PaymentCompletedEvent"),
    @JsonSubTypes.Type(value = PaymentFailedEvent.class, name = "PaymentFailedEvent"),
    @JsonSubTypes.Type(value = OrderReservationFailedEvent.class, name = "OrderReservationFailedEvent")
})
public interface BaseEvent {
  UUID getAggregateId();
}
