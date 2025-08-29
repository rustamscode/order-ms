package rustamscode.order_ms.scheduler.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.order.Outbox;
import rustamscode.order_ms.scheduler.OutboxScheduler;
import rustamscode.order_ms.service.OutboxService;
import rustamscode.order_ms.service.impl.OutboxProcessor;

import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "outbox.scheduler", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
public class OutboxSchedulerImpl implements OutboxScheduler {

  private final OutboxService outboxService;

  private final OutboxProcessor outboxProcessor;

  @Value("${outbox.scheduler.process-batch-size}")
  private Integer limit;

  @Override
  @Scheduled(initialDelay = 1000, fixedDelay = 4000)
  @SchedulerLock(
      name = "outbox_scheduler_process_outbox_event",
      lockAtMostFor = "10m",
      lockAtLeastFor = "1s")
  public void processOutboxEvent() {
    log.trace("Запуск обработки outbox-задач...");
    List<Outbox> outboxTasks = outboxService.claimTasksForProcessing(limit);

    log.info("Найдено {} задач для отправки в Kafka.", outboxTasks.size());
    outboxTasks.forEach(task -> {
      try {
        outboxProcessor.process(task);
      } catch (Exception e) {
        log.error("Критическая ошибка при вызове outboxProcessor для задачи {}. Цикл продолжается.", task.getId(), e);
      }
    });
  }
}
