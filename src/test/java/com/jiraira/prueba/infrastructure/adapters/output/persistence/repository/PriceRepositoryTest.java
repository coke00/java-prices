package com.jiraira.prueba.infrastructure.adapters.output.persistence.repository;

import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void findPriceByProductIdBrandIdAndApplicationDate() {
        PriceEntity price = PriceEntity.builder().productId(35455)
                .brandId(1)
                .startDate(LocalDateTime.parse("2020-06-13T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .curr("EUR")
                .build();
        entityManager.persist(price);
        entityManager.flush();

        List<PriceEntity> priceDTOEntities = priceRepository.findPriceByProductIdBrandIdAndApplicationDate(35455, 1, LocalDateTime.parse("2020-06-14T00:00:00"));

        assertFalse(priceDTOEntities.isEmpty(), "La lista de precios no debería estar vacía");
        assertEquals(1, priceDTOEntities.size(), "Debería haber un precio devuelto");
        assertEquals(price.getPrice(), priceDTOEntities.get(0).getPrice(), "El precio devuelto debería coincidir con el precio esperado");
    }

    @Test
    void findPriceByProductIdBrandIdAndApplicationDate_ReturnEmpty() {
        List<PriceEntity> priceDTOEntities = priceRepository.findPriceByProductIdBrandIdAndApplicationDate(35455, 1, LocalDateTime.parse("2020-06-14T00:00:00"));

        assertTrue(priceDTOEntities.isEmpty(), "La lista de precios está vacía");
        assertEquals(0, priceDTOEntities.size(), "Debería haber un precio devuelto");
    }
}
