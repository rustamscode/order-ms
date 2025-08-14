package rustamscode.order_ms.service;

import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;

import java.util.List;
import java.util.UUID;

public interface OutboxService {

  UUID createOutboxTask(OutboxCreateRq request);

  List<Outbox> getAllByStatus(OutboxStatus status, int limit);

  boolean updateStatusTo(OutboxStatus status, Outbox outboxTask);
}
