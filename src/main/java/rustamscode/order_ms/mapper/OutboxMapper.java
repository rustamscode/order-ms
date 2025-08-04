package rustamscode.order_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.order.Outbox;

@Mapper(componentModel = "spring")
public abstract class OutboxMapper {

  @Mapping(target = "status", defaultValue = "java(OutboxStatus.NEW)")
  public abstract Outbox mapToOutbox(OutboxCreateRq request);
}
