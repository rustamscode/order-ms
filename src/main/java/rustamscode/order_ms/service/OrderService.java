package rustamscode.order_ms.service;

import rustamscode.order_ms.dto.OrderCreateRq;

import java.util.UUID;

public interface OrderService {

  UUID createOrder(OrderCreateRq request);
}
