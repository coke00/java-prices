package com.jiraira.prueba.infrastructure.adapters.input.rest.mapper;

import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PriceRestMapperTest {
    private PriceRestMapper priceRestMapper;

    @BeforeEach
    void setUp() {
        priceRestMapper = new PriceRestMapper();
    }

    @Test
    void testToPriceResponse() {
        Price price = Price.builder().price(BigDecimal.valueOf(100.0)).priceList(1).build();
        PriceResponse pricesResponse = priceRestMapper.toPriceResponse(price);
        assertEquals(price.getPrice(), pricesResponse.getPrice());
        assertEquals(price.getPriceList(), pricesResponse.getPriceList());
    }


}
