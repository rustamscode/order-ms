package rustamscode.order_ms.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.BaseEvent;

@Slf4j
@Component
public class FallbackEventHandler implements EventHandler {

  @Override
  public void handle(BaseEvent event) {
    log.warn("This kind of payload is not supported");
  }

  @Override
  public Class<?> getEventType() {
    log.warn("This kind of event is not supported");
    return BaseEvent.class;
  }
}
