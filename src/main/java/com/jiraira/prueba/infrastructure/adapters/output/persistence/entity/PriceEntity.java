package com.jiraira.prueba.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRICE")
public class PriceEntity {
    @Id
    @Column(name = "PRICE_LIST")
    private Integer priceList;
    @Column(name = "BRAND_ID")
    private Integer brandId;
    @Column(name = "START_DATE")
    private LocalDateTime startDate;
    @Column(name = "END_DATE")
    private LocalDateTime endDate;
    @Column(name = "PRODUCT_ID")
    private Integer productId;
    @Column(name = "PRIORITY")
    private Integer priority;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "CURRENCY")
    private String curr;
    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;
    @Column(name = "LAST_UPDATE_BY")
    private String lastUpdateBy;
}
