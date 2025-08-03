package rustamscode.order_ms.entity.payload;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)

@JsonSubTypes({
    @JsonSubTypes.Type(value = OrderCreatedPayload.class, name = "OrderCreated"),
    @JsonSubTypes.Type(value = OrderCancelledPayload.class, name = "OrderCancelled")
})
public interface OutboxPayload {
}
