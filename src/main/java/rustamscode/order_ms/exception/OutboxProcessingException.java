package rustamscode.order_ms.exception;

public class OutboxProcessingException extends RuntimeException {
  public OutboxProcessingException() {
    super();
  }

  public OutboxProcessingException(String message) {
    super(message);
  }

  public OutboxProcessingException(String message, Throwable cause) {
    super(message, cause);
  }
}
