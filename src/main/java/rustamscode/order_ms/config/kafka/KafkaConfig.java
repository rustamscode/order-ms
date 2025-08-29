package rustamscode.order_ms.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  @Bean
  public NewTopic orderCreationTopic() {
    return TopicBuilder.name("order-event-topic")
        .partitions(3)
        .replicas(1)
        .config("cleanup.policy", "delete")
        .config("retention.ms", "604800000")
        .build();
  }
  // [ filter consumer]
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> filteringConcurrentKafkaListenerContainerFactory(
      ConsumerFactory<String, Object> consumerFactory,
      RecordFilterStrategy<Object, Object> compositeFilterStrategy) {

    ConcurrentKafkaListenerContainerFactory<String, Object> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
    containerFactory.setConsumerFactory(consumerFactory);
    containerFactory.setRecordFilterStrategy(compositeFilterStrategy);

    return containerFactory;
  }
}
