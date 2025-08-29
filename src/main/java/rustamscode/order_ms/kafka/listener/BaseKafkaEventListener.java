package rustamscode.order_ms.kafka.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.BaseEvent;
import rustamscode.order_ms.kafka.dispatcher.EventDispatcher;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseKafkaEventListener {

  private final EventDispatcher eventDispatcher;

  @KafkaListener(
      topics = {"payment-events-topic", "warehouse-events-topic"},
      groupId = "${spring.kafka.consumer.group-id}",
      containerFactory = "filteringConcurrentKafkaListenerContainerFactory"
  )
  public void handlePaymentEvent(@Payload BaseEvent payload,
                                 @Header(KafkaHeaders.RECEIVED_KEY) String key,
                                 @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                                 @Header(KafkaHeaders.OFFSET) long offset,
                                 Acknowledgment ack) {
    log.info("Получено событие о платеже. Топик: {}, Партиция: {}, Офсет: {}, Ключ: {}, Тело: {}",
        topic, partition, offset, key, payload);

    try {
      eventDispatcher.dispatch(payload);
      ack.acknowledge();
    } catch (Exception e) {
      log.error("Ошибка обработки события: offset={}, error={}", offset, e.getMessage(), e);
      ack.acknowledge();
    }
  }
}

