package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.BaseEntity;
import rustamscode.order_ms.entity.enums.OrderStatus;
import rustamscode.order_ms.entity.order.Order;
import rustamscode.order_ms.entity.payload.OrderCompletedEvent;
import rustamscode.order_ms.entity.payload.OrderFailedEvent;
import rustamscode.order_ms.mapper.OrderMapper;
import rustamscode.order_ms.repository.OrderRepository;
import rustamscode.order_ms.service.OrderService;
import rustamscode.order_ms.service.OutboxService;
import rustamscode.order_ms.util.EventConstants;

import java.util.List;
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
  public void createOrder(List<OrderCreateRq> request) {
    List<Order> orderList = request.stream()
        .map(orderMapper::mapToOrder)
        .toList();
    List<Order> orders = orderRepository.saveAll(orderList);
    List<UUID> orderIds = orders.stream().map(BaseEntity::getId).toList();
    log.info("Orders with ids {} have been saved", orderIds);

    List<OutboxCreateRq> outboxCreateRequests = orders
        .stream()
        .map(orderMapper::mapToOrderCreatedEvent)
        .map(event -> {
          return OutboxCreateRq
              .builder()
              .payload(event)
              .eventType(EventConstants.ORDER_CREATED)
              .topicName(orderEventTopic)
              .build();
        })
        .toList();

    outboxService.createOutboxTasks(outboxCreateRequests);
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
    outboxService.createOutboxTasks(
        List.of(OutboxCreateRq.builder()
            .payload(event)
            .eventType(EventConstants.ORDER_COMPLETED)
            .topicName(orderEventTopic)
            .build())
    );

    return true;
  }

  @Override
  @Transactional
  public boolean markOrderAsFailed(UUID id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(String.format("Order with id %s doesn't exist", id)));

    if (order.getStatus() == OrderStatus.FAILED) {
      return true;
    }

    order.setStatus(OrderStatus.FAILED);
    orderRepository.save(order);

    OrderFailedEvent event = orderMapper.mapToOrderFailedEvent(order);
    outboxService.createOutboxTasks(
        List.of(OutboxCreateRq.builder()
            .payload(event)
            .eventType(EventConstants.ORDER_FAILED)
            .topicName(orderEventTopic)
            .build())
    );

    return true;
  }
}
