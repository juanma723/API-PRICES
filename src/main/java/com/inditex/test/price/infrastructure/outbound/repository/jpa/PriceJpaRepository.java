package com.inditex.test.price.infrastructure.outbound.repository.jpa;

import com.inditex.test.price.infrastructure.outbound.repository.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {


    @Query(value = "SELECT * FROM PRICES p " +
            "WHERE p.BRAND_ID = :brandId " +
            "AND p.START_DATE  <= :startDate " +
            "AND p.END_DATE >= :startDate " +
            "AND p.PRODUCT_ID = :productId " +
            "ORDER BY p.PRIORITY DESC" +
            " LIMIT 1", nativeQuery = true)
    Optional<PriceEntity> findByFilter(@Param("brandId") Long brandId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("productId") Long productId);


}
