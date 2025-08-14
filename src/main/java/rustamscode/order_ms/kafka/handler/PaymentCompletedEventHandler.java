package rustamscode.order_ms.kafka.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.PaymentCompletedEvent;
import rustamscode.order_ms.service.OrderService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCompletedEventHandler implements EventHandler<PaymentCompletedEvent> {

  private final OrderService orderService;

  @Override
  public void handle(PaymentCompletedEvent event) {
    if (event == null || event.aggregateId() == null) {
      throw new IllegalArgumentException("Invalid PaymentCompletedEvent");
    }

    orderService.finalizeOrderCreation(event.aggregateId());
  }

  @Override
  public Class<PaymentCompletedEvent> getEventType() {
    return PaymentCompletedEvent.class;
  }
}
