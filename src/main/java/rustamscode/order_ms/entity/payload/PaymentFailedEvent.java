package rustamscode.order_ms.entity.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PaymentFailedEvent implements BaseEvent {
  private @NotNull UUID id;
  private @NotNull UUID aggregateId;
  private @NotNull UUID customerId;
  private @NotNull String message;
}
