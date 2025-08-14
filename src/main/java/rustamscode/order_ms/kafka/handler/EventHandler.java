package rustamscode.order_ms.kafka.handler;

import rustamscode.order_ms.entity.payload.BaseEvent;

public interface EventHandler<T extends BaseEvent> {

  void handle(T event);

  Class<T> getEventType();
}
