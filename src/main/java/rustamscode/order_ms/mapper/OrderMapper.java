package rustamscode.order_ms.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.entity.enums.OrderStatus;
import rustamscode.order_ms.entity.order.Order;
import rustamscode.order_ms.entity.payload.OrderCompletedEvent;
import rustamscode.order_ms.entity.payload.OrderCreatedEvent;
import rustamscode.order_ms.entity.payload.OrderFailedEvent;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class}, imports = {OrderStatus.class, UUID.class})
public abstract class OrderMapper {

  @Mapping(target = "customerId", source = "customerId")
  @Mapping(target = "items", source = "items")
  @Mapping(target = "status", expression = "java(OrderStatus.PENDING)")
  public abstract Order mapToOrder(OrderCreateRq request);

  @Mapping(target = "id", defaultExpression = "java(UUID.randomUUID())")
  @Mapping(target = "aggregateId", source = "id")
  public abstract OrderCreatedEvent mapToOrderCreatedEvent(Order order);

  @Mapping(target = "id", defaultExpression = "java(UUID.randomUUID())")
  @Mapping(target = "aggregateId", source = "id")
  public abstract OrderCompletedEvent mapToOrderCompletedEvent(Order order);

  @Mapping(target = "id", defaultExpression = "java(UUID.randomUUID())")
  @Mapping(target = "aggregateId", source = "id")
  public abstract OrderFailedEvent mapToOrderFailedEvent(Order order);

  @AfterMapping
  protected void enrichOrder(@MappingTarget Order order) {
    order.getItems()
        .forEach(item -> item.setOrder(order));

    BigDecimal totalPrice = order
        .getItems()
        .stream()
        .map(item -> item.getPricePerUnit().multiply(item.getQuantity()))
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);

    order.setTotalPrice(totalPrice);
  }
}
