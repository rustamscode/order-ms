package rustamscode.order_ms.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BaseKafkaProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void produceSync(String topic, String key, Object payload) throws ExecutionException, InterruptedException, TimeoutException {
    log.debug("Отправка сообщения в топик [{}], ключ [{}]: {}", topic, key, payload);

    SendResult<String, Object> result = kafkaTemplate.send(topic, key, payload)
        .get(30, TimeUnit.SECONDS);

    log.info("Сообщение успешно отправлено. Топик: {}, Партиция: {}, Офсет: {}",
        result.getRecordMetadata().topic(),
        result.getRecordMetadata().partition(),
        result.getRecordMetadata().offset());
  }

  public CompletableFuture<SendResult<String, Object>> produceAsync(String topic, String key, Object payload) {
    throw new UnsupportedOperationException("Method is not implemented yet");
  }
}
