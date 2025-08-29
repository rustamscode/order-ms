package rustamscode.order_ms.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import rustamscode.order_ms.controller.OrderController;
import rustamscode.order_ms.dto.OrderCreateRq;
import rustamscode.order_ms.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

  private final OrderService orderService;

  @Override
  public ResponseEntity<Void> createOrder(List<OrderCreateRq> request) {
    orderService.createOrder(request);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .build();
  }
}
