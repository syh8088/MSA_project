package kiwi.shop.order.adapter.out.persistence.repository;

import kiwi.shop.order.adapter.out.persistence.entity.OutBox;
import kiwi.shop.order.domain.OutBoxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OutBoxRepository extends JpaRepository<OutBox, Long>, OutBoxRepositoryCustom {

    @Modifying
    @Query("UPDATE OutBox AS o SET o.status = :outBoxStatus WHERE o.idempotencyKey = :idempotencyKey")
    void updateStatusByIdempotencyKey(@Param("idempotencyKey") String idempotencyKey, @Param("outBoxStatus") OutBoxStatus outBoxStatus);
}