package rustamscode.order_ms.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rustamscode.order_ms.entity.enums.OutboxStatus;
import rustamscode.order_ms.entity.order.Outbox;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, UUID> {

  @Query(value = """
      SELECT * FROM outbox
      WHERE status = :status
      ORDER BY created_date ASC
      LIMIT :limit
      FOR UPDATE SKIP LOCKED
      """, nativeQuery = true)
  List<Outbox> findAllAndLockByStatus(@Param("status") String status,
                                      @Param("limit") Integer limit);
}
