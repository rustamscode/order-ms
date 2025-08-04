package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.order.Order;
import rustamscode.order_ms.entity.payload.OrderCreatedPayload;
import rustamscode.order_ms.entity.payload.OutboxPayload;
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

  @Override
  @Transactional
  public UUID createOrder(OrderCreateRq request) {
    Order savedOrder = orderRepository.save(orderMapper.mapToOrder(request));
    UUID orderId = savedOrder.getId();
    log.info("Order with id {} has been saved", orderId);

    OrderCreatedPayload outboxPayload = orderMapper.mapToOrderCreatedPayload(savedOrder);
    OutboxCreateRq outboxRequest = OutboxCreateRq.builder()
        .aggregateId(orderId)
        .payload(outboxPayload)
        .eventType(EventConstants.ORDER_CREATED)
        .topicName(null)
        .build();
    outboxService.createOutboxTask(outboxRequest);

    return orderId;
  }
}
