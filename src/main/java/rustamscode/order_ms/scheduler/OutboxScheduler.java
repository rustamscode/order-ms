package rustamscode.order_ms.scheduler;

public interface OutboxScheduler {

  void processOutboxEvent();
}
