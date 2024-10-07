package com.jiraira.prueba.infrastructure.adapters.output.persistence;

import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.mapper.PricePersistenceMapper;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.repository.PriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class PricePersistenceAdapterTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;
    @Mock
    private PricePersistenceMapper pricePersistenceMapper;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindPriceApplicableByProductIdBrandIdAndDate() {
        Integer productId = 1;
        Integer brandId = 1;
        LocalDateTime applicationDate = LocalDateTime.now();
        List<PriceEntity> expectedPriceEntity = Collections.singletonList(new PriceEntity());
        List<Price> expectedPrice = Collections.singletonList(new Price());

        when(priceRepository.findPriceByProductIdBrandIdAndApplicationDate(productId, brandId, applicationDate))
                .thenReturn(expectedPriceEntity);
        when(pricePersistenceMapper.toPrices(expectedPriceEntity))
                .thenReturn(expectedPrice);

        List<Price> actualPriceDTOS = pricePersistenceAdapter.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate);

        assertEquals(expectedPrice, actualPriceDTOS);
    }
}
