package rustamscode.order_ms.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;
import rustamscode.order_ms.kafka.producer.BaseKafkaProducer;
import rustamscode.order_ms.service.OutboxService;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxProcessor {

  private final OutboxService outboxService;

  private final BaseKafkaProducer kafkaProducer;

  @Transactional
  public void process(Outbox task) {
    if (Objects.isNull(task)) {
      throw new IllegalArgumentException("Event for processing can not be null");
    }

    try {
      kafkaProducer.produceSync(task.getTopicName(), task.getPayload().getAggregateId().toString(), task.getPayload());
      outboxService.updateStatusTo(OutboxStatus.SENT, task);
      log.info("Outbox task has successfully been sent to kafka");
    } catch (ExecutionException | InterruptedException | TimeoutException e) {
      handleFailure(task, e);
    }
  }

  private void handleFailure(Outbox task, Exception e) {
    log.warn("Task with id {} couldn't be sent to kafka: {}", task.getId(), e.getCause().toString());
    outboxService.updateStatusTo(OutboxStatus.FAILED, task);
  }
}
