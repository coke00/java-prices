package com.jiraira.prueba.infrastructure.adapters.output.persistence;


import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.domain.ports.output.PriceOutputPort;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.entity.PriceEntity;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.mapper.PricePersistenceMapper;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.repository.PriceRepository;

import java.time.LocalDateTime;
import java.util.List;


public class PricePersistenceAdapter implements PriceOutputPort {

    private final PriceRepository priceRepository;
    private final PricePersistenceMapper pricePersistenceMapper;

    public PricePersistenceAdapter(PriceRepository priceRepository, PricePersistenceMapper pricePersistenceMapper) {
        this.priceRepository = priceRepository;
        this.pricePersistenceMapper = pricePersistenceMapper;
    }

    @Override
    public List<Price> findPriceApplicableByProductIdBrandIdAndDate(Integer productId, Integer brandId, LocalDateTime applicationDate) {
        List<PriceEntity> priceEntity = priceRepository.findPriceByProductIdBrandIdAndApplicationDate(productId, brandId, applicationDate);
        return pricePersistenceMapper.toPrices(priceEntity);

    }

}
