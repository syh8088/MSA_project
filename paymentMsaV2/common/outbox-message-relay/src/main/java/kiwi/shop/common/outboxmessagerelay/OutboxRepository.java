package kiwi.shop.common.outboxmessagerelay;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<Outbox, Long> {

    List<Outbox> findAllByShardKeyAndCreatedDateTimeLessThanEqualOrderByCreatedDateTimeAsc(
            Long shardKey,
            LocalDateTime from,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE Outbox AS o SET o.status = :outBoxStatus WHERE o.idempotencyKey = :idempotencyKey")
    void updateStatusByIdempotencyKey(String orderId, OutBoxStatus outBoxStatus);
}
