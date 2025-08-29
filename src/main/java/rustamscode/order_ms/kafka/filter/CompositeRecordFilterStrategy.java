package rustamscode.order_ms.kafka.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.annotation.DelegateRecordFilterStrategy;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompositeRecordFilterStrategy implements RecordFilterStrategy<Object, Object> {

  @DelegateRecordFilterStrategy
  private final List<RecordFilterStrategy<Object, Object>> delegates;

  /**
   * @return true, если сообщение нужно ОТБРОСИТЬ.
   * false, если сообщение нужно ОБРАБОТАТЬ.
   */
  @Override
  public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
    return delegates.stream()
        .anyMatch(delegate -> delegate.filter(consumerRecord));
  }
}
