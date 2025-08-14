package rustamscode.order_ms.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  @Bean
  public NewTopic orderCreationTopic() {
    return TopicBuilder.name("order-creation-events")
        .partitions(3)
        .replicas(3)
        .config("cleanup.policy", "delete")
        .config("retention.ms", "604800000")
        .config("min.insync.replicas", "2")
        .build();
  }
}
