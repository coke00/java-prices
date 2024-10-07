package com.jiraira.prueba.infrastructure.adapters.output.persistence.mapper;

import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricePersistenceMapperTest {
    private PricePersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PricePersistenceMapper();
    }

    @Test
    void testToPrices() {
        PriceEntity priceEntity = PriceEntity.builder()
                .priceList(1)
                .price(BigDecimal.valueOf(100.0))
                .build();

        List<PriceEntity> priceEntities = List.of(priceEntity);
        List<Price> prices = mapper.toPrices(priceEntities);

        assertEquals(1, prices.size());
        assertEquals(1, prices.get(0).getPriceList());
        assertEquals(BigDecimal.valueOf(100.0), prices.get(0).getPrice());
    }
}
