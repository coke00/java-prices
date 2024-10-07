package com.jiraira.prueba.infrastructure.adapters.output.persistence.mapper;

import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;

import java.util.List;


public class PricePersistenceMapper {

    public List<Price> toPrices(List<PriceEntity> pricesEntity) {
        return pricesEntity.stream().map(this::toPrice).toList();
    }

    public Price toPrice(PriceEntity pricesEntity) {
        return Price.builder()
                .brandId(pricesEntity.getBrandId())
                .productId(pricesEntity.getProductId())
                .priceList(pricesEntity.getPriceList())
                .startDate(pricesEntity.getStartDate())
                .endDate(pricesEntity.getEndDate())
                .price(pricesEntity.getPrice())
                .curr(pricesEntity.getCurr())
                .priority(pricesEntity.getPriority())
                .lastUpdate(pricesEntity.getLastUpdate())
                .lastUpdateBy(pricesEntity.getLastUpdateBy())
                .build();
    }
}
