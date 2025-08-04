package rustamscode.order_ms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class OrderItemDto {

  @NotNull
  UUID productId;

  @NotNull
  @Positive
  BigDecimal quantity;

  @NotNull
  @Positive
  BigDecimal pricePerUnit;
}
