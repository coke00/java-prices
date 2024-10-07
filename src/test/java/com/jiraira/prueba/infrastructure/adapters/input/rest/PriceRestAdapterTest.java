package com.jiraira.prueba.infrastructure.adapters.input.rest;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.domain.ports.input.GetPriceUseCase;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.PriceResponse;
import com.jiraira.prueba.infrastructure.adapters.input.rest.mapper.PriceRestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PriceRestAdapterTest {

    @InjectMocks
    private PriceRestAdapter priceRestAdapter;
    @Mock
    private GetPriceUseCase priceService;
    @Mock
    private PriceRestMapper priceRestMapper;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.US);

    }

    @Test
    void testGetPriceReturnsPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Integer productId = 35455;
        Integer brandId = 1;

        Price mockPrice = Price.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .productId(35455)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();
        PriceResponse mockPriceResponse = PriceResponse.builder()
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .productId(35455)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();

        when(priceService.findApplicablePrice(productId, brandId, applicationDate)).thenReturn(mockPrice);
        when(priceRestMapper.toPriceResponse(mockPrice)).thenReturn(mockPriceResponse);

        ResponseEntity<?> priceResponse = priceRestAdapter.getPrice(applicationDate, productId, brandId);

        assertEquals(HttpStatus.OK, priceResponse.getStatusCode(), "Debería haber una respuesta de estado");
        assertEquals(mockPriceResponse, priceResponse.getBody(), "Debería haber un precio devuelto");

    }

    @Test
    void testGetPriceThrowsPriceNotFoundException() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
        Integer productId = 35455;
        Integer brandId = 1;

        when(priceService.findApplicablePrice(productId, brandId, applicationDate)).thenThrow(new PriceNotFoundException("Price not found"));

        assertThrows(PriceNotFoundException.class, () -> {
            priceRestAdapter.getPrice(applicationDate, productId, brandId);
        }, "Debería lanzar PriceNotFoundException");
    }
}
