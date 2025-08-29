package rustamscode.order_ms.service;

import rustamscode.order_ms.dto.OrderCreateRq;

import java.util.List;
import java.util.UUID;

public interface OrderService {

  void createOrder(List<OrderCreateRq> request);

  boolean finalizeOrderCreation(UUID id);

  boolean markOrderAsFailed(UUID id);
}
