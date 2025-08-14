package rustamscode.order_ms.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import rustamscode.order_ms.dto.OutboxCreateRq;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;

@Mapper(componentModel = "spring", imports = {OutboxStatus.class})
public abstract class OutboxMapper {

  @Mapping(target = "status", expression = "java(OutboxStatus.NEW)")
  public abstract Outbox mapToOutbox(OutboxCreateRq request);
}
