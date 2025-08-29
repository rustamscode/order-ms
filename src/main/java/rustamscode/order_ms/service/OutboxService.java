package rustamscode.order_ms.service;

import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;

import java.util.List;

public interface OutboxService {

  void createOutboxTasks(List<OutboxCreateRq> requests);

  List<Outbox> claimTasksForProcessing(int limit);

  boolean updateStatusTo(OutboxStatus status, Outbox outboxTask);
}
