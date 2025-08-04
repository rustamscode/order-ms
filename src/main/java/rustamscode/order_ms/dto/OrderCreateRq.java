package rustamscode.order_ms.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderCreateRq {

  @NotNull
  UUID customerId;

  @NotNull
  @NotEmpty
  List<OrderItemDto> items;
}
