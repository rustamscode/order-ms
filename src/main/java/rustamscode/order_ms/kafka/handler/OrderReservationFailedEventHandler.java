package rustamscode.order_ms.kafka.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.OrderReservationFailedEvent;
import rustamscode.order_ms.service.OrderService;

@Component
@RequiredArgsConstructor
public class OrderReservationFailedEventHandler implements EventHandler<OrderReservationFailedEvent> {

  private final OrderService orderService;

  @Override
  public void handle(OrderReservationFailedEvent event) {
    orderService.markOrderAsFailed(event.getAggregateId());
  }

  @Override
  public Class<OrderReservationFailedEvent> getEventType() {
    return OrderReservationFailedEvent.class;
  }
}
