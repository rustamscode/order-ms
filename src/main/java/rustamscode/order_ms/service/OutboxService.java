package rustamscode.order_ms.service;

import rustamscode.order_ms.dto.OutboxCreateRq;

import java.util.UUID;

public interface OutboxService {

  UUID createOutboxTask(OutboxCreateRq request);
}
