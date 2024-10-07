package com.jiraira.prueba.application.service;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.domain.ports.output.PriceOutputPort;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.PriceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PriceServiceTest {
    @InjectMocks
    private PriceService priceService;

    @Mock
    private PriceOutputPort priceOutputPort;

    @Test
    void whenFindApplicablePrice_thenReturnSingleApplicablePrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;
        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(Collections.singletonList(getPriceMock()));
        Price expectedPriceResponseDTO = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .price(new BigDecimal("30.50"))
                .curr("EUR")
                .build();
        Price price = priceService.findApplicablePrice(productId, brandId, applicationDate);
        assertEquals(expectedPriceResponseDTO.getPrice(), price.getPrice());
        assertEquals(expectedPriceResponseDTO.getPriceList(), price.getPriceList());
        assertEquals(expectedPriceResponseDTO.getBrandId(), price.getBrandId());
    }


    @Test
    void whenFindApplicablePrice_thenReturnMultipleApplicablePriceSelectingMaxPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;
        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(getPricesMultipleApplicablePrice());

        Price priceExpected = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .productId(1)
                .price(new BigDecimal("40.50"))
                .curr("EUR")
                .build();
        Price price = priceService.findApplicablePrice(productId, brandId, applicationDate);

        assertEquals(priceExpected.getPriceList(), price.getPriceList());
        assertEquals(priceExpected.getCurr(), price.getCurr());
        assertEquals(priceExpected.getPrice(), price.getPrice());
        assertEquals(priceExpected.getPriceList(), price.getPriceList());
    }


    @Test
    void whenFindApplicablePrice_thenReturnEmptyOptional() {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;
        Price expectedPrice = Price.builder().build();
        PriceResponse expectedPriceResponseDTO = PriceResponse.builder().build();
        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(Collections.singletonList(expectedPrice));

        Price price = priceService.findApplicablePrice(productId, brandId, applicationDate);
        assertEquals(price.getPriceList(), expectedPriceResponseDTO.getPriceList());
        assertEquals(price.getPrice(), expectedPriceResponseDTO.getPrice());
        assertEquals(price.getCurr(), expectedPriceResponseDTO.getCurr());
    }

    @Test
    void whenFindApplicablePrice_thenReturnEmptyOptionalWithThrow() throws PriceNotFoundException {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;
        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(List.of());

        assertThrows(PriceNotFoundException.class, () ->
                        priceService.findApplicablePrice(productId, brandId, applicationDate),
                "Expected PriceNotFoundException to be thrown when no price is found"
        );
    }

    @Test
    void whenNoApplicablePriceFound_thenThrowPriceNotFoundException() {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;
        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(Collections.emptyList());

        assertThrows(PriceNotFoundException.class, () ->
                priceService.findApplicablePrice(productId, brandId, applicationDate));
    }

    @Test
    void whenMultiplePricesWithSamePriority_thenReturnFirstOne() {
        LocalDateTime applicationDate = LocalDateTime.of(2024, 2, 6, 10, 0);
        Integer productId = 1;
        Integer brandId = 2;

        when(priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate))
                .thenReturn(getPricesMocks());
        Price priceExpected1 = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .productId(1)
                .price(new BigDecimal("30.50"))
                .curr("EUR")
                .build();
        Price priceResponse = priceService.findApplicablePrice(productId, brandId, applicationDate);
        assertEquals(priceExpected1.getPrice(), priceResponse.getPrice());
        assertEquals(priceExpected1.getPriceList(), priceResponse.getPriceList());
        assertEquals(priceExpected1.getCurr(), priceResponse.getCurr());
    }

    @Test
    void whenNullInputParameters_thenThrowPriceNotFoundException() {
        assertThrows(PriceNotFoundException.class, () ->
                priceService.findApplicablePrice(null, null, null));
    }

    private static Price getPriceMock() {
        return Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .priority(0)
                .price(new BigDecimal("30.50"))
                .curr("EUR")
                .build();
    }

    private static List<Price> getPricesMocks() {
        List<Price> prices = new ArrayList<>();
        Price price1 = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .priority(1)
                .productId(1)
                .price(new BigDecimal("30.50"))
                .curr("EUR")
                .build();
        Price price2 = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(2)
                .priority(1)
                .productId(1)
                .price(new BigDecimal("40.50"))
                .curr("EUR")
                .build();
        prices.add(price1);
        prices.add(price2);
        return prices;
    }

    private static List<Price> getPricesMultipleApplicablePrice() {
        List<Price> expectedPriceListEntity = new ArrayList<>();
        Price price1 = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .priority(0)
                .productId(1)
                .price(new BigDecimal("30.50"))
                .curr("EUR")
                .build();
        Price price2 = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .priority(1)
                .productId(1)
                .price(new BigDecimal("40.50"))
                .curr("EUR")
                .build();
        expectedPriceListEntity.add(price1);
        expectedPriceListEntity.add(price2);
        return expectedPriceListEntity;
    }
}
