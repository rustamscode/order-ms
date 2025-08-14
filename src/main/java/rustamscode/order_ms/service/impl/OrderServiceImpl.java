package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.enums.OrderStatus;
import rustamscode.order_ms.entity.order.Order;
import rustamscode.order_ms.entity.payload.OrderCompletedEvent;
import rustamscode.order_ms.entity.payload.OrderCreatedEvent;
import rustamscode.order_ms.mapper.OrderMapper;
import rustamscode.order_ms.repository.OrderRepository;
import rustamscode.order_ms.service.OrderService;
import rustamscode.order_ms.service.OutboxService;
import rustamscode.order_ms.util.EventConstants;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OutboxService outboxService;

  private final OrderRepository orderRepository;

  private final OrderMapper orderMapper;

  @Value("${kafka.order-event-topic}")
  private String orderEventTopic;

  @Override
  @Transactional
  public UUID createOrder(OrderCreateRq request) {
    Order savedOrder = orderRepository.save(orderMapper.mapToOrder(request));
    UUID orderId = savedOrder.getId();
    log.info("Order with id {} has been saved", orderId);

    OrderCreatedEvent event = orderMapper.mapToOrderCreatedPayload(savedOrder);
    OutboxCreateRq outboxRequest = OutboxCreateRq.builder()
        .payload(event)
        .eventType(EventConstants.ORDER_CREATED)
        .topicName(orderEventTopic)
        .build();
    outboxService.createOutboxTask(outboxRequest);

    return orderId;
  }

  @Override
  @Transactional
  public boolean finalizeOrderCreation(UUID id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Order with id %s doesn't exist", id)));

    if (order.getStatus() == OrderStatus.COMPLETED) {
      return true;
    }

    order.setStatus(OrderStatus.COMPLETED);
    orderRepository.save(order);

    OrderCompletedEvent event = orderMapper.mapToOrderCompletedEvent(order);
    outboxService.createOutboxTask(
        OutboxCreateRq.builder()
            .payload(event)
            .eventType(EventConstants.ORDER_COMPLETED)
            .topicName(orderEventTopic)
            .build()
    );

    return true;
  }
}
