package rustamscode.order_ms.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EventConstants {

  public static final String ORDER_CREATED = "OrderCreatedEvent";
  public static final String ORDER_CANCELLED = "OrderCancelledEvent";
  public static final String ORDER_COMPLETED = "OrderCompletedEvent";
  public static final String ORDER_FAILED = "OrderFailedEvent";
}
