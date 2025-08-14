package rustamscode.order_ms.entity.payload;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PaymentFailedEvent(

    @NotNull
    UUID id,

    @NotNull
    UUID aggregateId,

    @NotNull
    UUID customerId,

    @NotNull
    String message) implements BaseEvent {
}
