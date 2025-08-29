package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.BaseEntity;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;
import rustamscode.order_ms.mapper.OutboxMapper;
import rustamscode.order_ms.repository.OutboxRepository;
import rustamscode.order_ms.service.OutboxService;

import java.util.Collections;
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
  public void createOutboxTasks(List<OutboxCreateRq> requests) {
    List<Outbox> outboxList = requests
        .stream()
        .map(outboxMapper::mapToOutbox)
        .toList();

    List<UUID> savedOutboxList = outboxRepository.saveAll(outboxList)
        .stream()
        .map(BaseEntity::getId)
        .toList();
    log.info("Outbox events with ids {} have been saved", savedOutboxList);
  }

  @Override
  @Transactional
  public List<Outbox> claimTasksForProcessing(int limit) {
    if (limit == 0) {
      throw new IllegalArgumentException("Limit has to be greater than 0");
    }

    List<Outbox> outboxTasks = outboxRepository.findAllAndLockByStatus(OutboxStatus.NEW.name(), limit);
    if (outboxTasks.isEmpty()) {
      log.info("Нет задач для обработки.");
      return Collections.emptyList();
    }

    outboxTasks.forEach(task -> task.setStatus(OutboxStatus.PROCESSING));
    return outboxRepository.saveAll(outboxTasks);
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
