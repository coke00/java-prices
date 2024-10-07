package com.jiraira.prueba.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    private Integer priceList;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer productId;
    private Integer priority;
    private BigDecimal price;
    private String curr;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
}
