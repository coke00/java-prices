package com.jiraira.prueba.infrastructure.adapters.output.persistence.repository;

import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<PriceEntity, Long> {
    @Query(value = "SELECT p FROM PriceEntity p WHERE p.productId = :productId AND p.brandId = :brandId AND p.startDate <= :applicationDate AND p.endDate >= :applicationDate")
    List<PriceEntity> findPriceByProductIdBrandIdAndApplicationDate(Integer productId, Integer brandId, LocalDateTime applicationDate);

}
