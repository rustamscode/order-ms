package rustamscode.order_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rustamscode.order_ms.dto.OrderItemDto;
import rustamscode.order_ms.entity.enums.OrderStatus;
import rustamscode.order_ms.entity.order.OrderItem;

@Mapper(componentModel = "spring", imports = OrderStatus.class)
public abstract class OrderItemMapper {

  @Mapping(target = "productId", source = "productId")
  @Mapping(target = "quantity", source = "quantity")
  @Mapping(target = "pricePerUnit", source = "pricePerUnit")
  public abstract OrderItem mapToOrderItem(OrderItemDto dto);
}
