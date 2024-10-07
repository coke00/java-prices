package com.jiraira.prueba.domain.ports.output;

import com.jiraira.prueba.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceOutputPort {
    List<Price> findPriceApplicableByProductIdBrandIdAndDate(Integer productId, Integer brandId, LocalDateTime applicationDate);
}
