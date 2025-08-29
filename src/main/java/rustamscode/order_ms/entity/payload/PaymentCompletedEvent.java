package rustamscode.order_ms.entity.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentCompletedEvent implements BaseEvent {
  private @NotNull UUID id;
  private @NotNull UUID aggregateId;
  private @NotNull UUID customerId;
  private @NotNull @Positive BigDecimal amount;
}
