package rustamscode.order_ms.kafka.dispatcher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.BaseEvent;
import rustamscode.order_ms.kafka.handler.EventHandler;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventDispatcher {

  private final List<EventHandler<?>> eventHandlers;

  private Map<Class<? extends BaseEvent>, EventHandler<?>> handlerMap;

  private final AtomicBoolean initialized = new AtomicBoolean(false);

  @EventListener(ApplicationReadyEvent.class)
  public void initialize() {
    if (initialized.compareAndSet(false, true)) {
      try {
        initHandlerMap();
      } catch (Exception e) {
        initialized.set(false);
        throw new IllegalStateException("Cannot initialize EventDispatcher", e);
      }
    }
  }

  private void initHandlerMap() {
    this.handlerMap = eventHandlers.stream()
        .collect(Collectors.toUnmodifiableMap(
            EventHandler::getEventType, Function.identity()
        ));
  }

  @SuppressWarnings("unchecked")
  public void dispatch(BaseEvent event) {
    if (!initialized.get()) {
      throw new IllegalStateException("EventDispatcher is not initialized");
    }

    if (event == null) {
      log.warn("Event for dispatching is null");
      return;
    }

    EventHandler handler = handlerMap.getOrDefault(event.getClass(), handlerMap.get(BaseEvent.class));
    handler.handle(event);
  }
}
