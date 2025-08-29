package rustamscode.order_ms.kafka.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.annotation.DelegateRecordFilterStrategy;
import rustamscode.order_ms.kafka.dispatcher.EventDispatcher;

import java.util.Set;

@Slf4j
@Component
@DelegateRecordFilterStrategy
public class EventTypeFilterStrategy implements RecordFilterStrategy<String, String> {

  private final ObjectMapper objectMapper;
  private final Set<String> availableEventTypes;

  @Autowired
  public EventTypeFilterStrategy(ObjectMapper objectMapper, EventDispatcher eventDispatcher) {
    this.objectMapper = objectMapper;
    this.availableEventTypes = eventDispatcher.getAvailableEventTypes();
  }

  @Override
  public boolean filter(ConsumerRecord<String, String> consumerRecord) {
    try {
      JsonNode payload = objectMapper.readTree(consumerRecord.value());
      JsonNode eventTypeNode = payload.get("eventType");

      if (eventTypeNode == null || !eventTypeNode.isTextual()) {
        log.warn("Сообщение не содержит поле 'eventType' или оно не является текстовым. Сообщение будет отброшено. Offset: {}", consumerRecord.offset());
        return true;
      }

      String eventType = eventTypeNode.asText();
      boolean isAllowed = availableEventTypes.contains(eventType);
      log.trace("Сообщение с eventType '{}' отфильтровано со статусом isAllowed: {}. Offset: {}", eventType, isAllowed, consumerRecord.offset());
      return isAllowed;
    } catch (Exception e) {
      log.error("Не удалось прочитать JSON из Kafka-сообщения для фильтрации. Сообщение будет отброшено. Offset: {}. Ошибка: {}",
          consumerRecord.offset(), e.getMessage());
      return true;
    }
  }
}
