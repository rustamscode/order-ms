package rustamscode.order_ms.kafka.dispatcher;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rustamscode.order_ms.entity.payload.BaseEvent;
import rustamscode.order_ms.kafka.handler.EventHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EventDispatcher {

  private final Map<Class<? extends BaseEvent>, EventHandler<?>> handlerMap;

  @Getter
  private final Set<String> availableEventTypes;

  @Autowired
  public EventDispatcher(List<EventHandler<?>> eventHandlers) {
    this.handlerMap = initHandlerMap(eventHandlers);
    this.availableEventTypes = initAvailableEventTypes();
  }

  private Map<Class<? extends BaseEvent>, EventHandler<?>> initHandlerMap(List<EventHandler<?>> eventHandlers) {
    return eventHandlers.stream()
        .collect(Collectors.toUnmodifiableMap(
            EventHandler::getEventType, Function.identity()
        ));
  }

  private Set<String> initAvailableEventTypes() {
    return handlerMap.keySet()
        .stream()
        .map(Class::getSimpleName)
        .collect(Collectors.toUnmodifiableSet());
  }

  @SuppressWarnings("unchecked")
  public void dispatch(BaseEvent event) {
    if (event == null) {
      log.warn("Event for dispatching is null");
      return;
    }

    EventHandler handler = handlerMap.getOrDefault(event.getClass(), handlerMap.get(BaseEvent.class));
    handler.handle(event);
  }
}
