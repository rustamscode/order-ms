package rustamscode.order_ms.entity.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent (

    @NotNull
    UUID id,

    @NotNull
    UUID aggregateId,

    @NotNull
    UUID customerId,

    @NotNull
    @Positive
    BigDecimal amount) implements BaseEvent {
}
