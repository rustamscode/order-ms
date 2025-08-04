package rustamscode.order_ms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import rustamscode.order_ms.dto.OrderCreateRq;

import java.util.UUID;

@Tag(name = "Order management API")
public interface OrderController {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create a order")
  ResponseEntity<UUID> createOrder(@RequestBody @Valid OrderCreateRq request);
}
