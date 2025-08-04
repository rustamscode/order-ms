package rustamscode.order_ms.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.entity.enums.OrderStatus;
import rustamscode.order_ms.entity.order.Order;
import rustamscode.order_ms.entity.payload.OrderCreatedPayload;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class}, imports = OrderStatus.class)
public abstract class OrderMapper {

  @Mapping(target = "customerId", source = "customerId")
  @Mapping(target = "items", source = "items")
  @Mapping(target = "status", defaultValue = "java(OrderStatus.PENDING)")
  public abstract Order mapToOrder(OrderCreateRq request);

  public abstract OrderCreatedPayload mapToOrderCreatedPayload(Order order);

  @AfterMapping
  private void enrichOrder(@MappingTarget Order order, OrderCreateRq request) {
    order.getItems()
        .forEach(item -> item.setOrder(order));

    BigDecimal totalPrice = order
        .getItems()
        .stream()
        .map(item -> item.getPricePerUnit().multiply(item.getQuantity()))
        .reduce(BigDecimal::add)
        .orElseThrow(RuntimeException::new);

    order.setTotalPrice(totalPrice);
  }
}
