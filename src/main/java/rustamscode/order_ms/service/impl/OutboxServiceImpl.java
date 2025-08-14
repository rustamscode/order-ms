package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;
import rustamscode.order_ms.mapper.OutboxMapper;
import rustamscode.order_ms.repository.OutboxRepository;
import rustamscode.order_ms.service.OutboxService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

  private final OutboxRepository outboxRepository;

  private final OutboxMapper outboxMapper;

  @Override
  @Transactional
  public UUID createOutboxTask(OutboxCreateRq request) {
    Outbox outbox = outboxMapper.mapToOutbox(request);
    UUID outboxId = outboxRepository.save(outbox).getId();

    log.info("Outbox event with id {} has been saved", outboxId);

    return outboxId;
  }

  @Override
  @Transactional
  public List<Outbox> getAllByStatus(OutboxStatus status, int limit) {
    if (status == null) {
      throw new IllegalArgumentException("Please provide correct status for outbox tasks");
    }

    return outboxRepository.findAllAndLockByStatus(status, limit);
  }

  @Override
  @Transactional
  public boolean updateStatusTo(OutboxStatus status, Outbox outboxTask) {
    outboxTask.setStatus(status);

    outboxRepository.save(outboxTask);
    log.info("Status of outbox task with id {} have been updated to {}", outboxTask.getId(), status);

    return true;
  }
}
