package com.jiraira.prueba.infrastructure.adapters.configuration;


import com.jiraira.prueba.application.service.PriceService;
import com.jiraira.prueba.infrastructure.adapters.input.rest.mapper.PriceRestMapper;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.PricePersistenceAdapter;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.mapper.PricePersistenceMapper;
import com.jiraira.prueba.infrastructure.adapters.output.persistence.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceRestMapper priceRestMapper() {
        return new PriceRestMapper();
    }

    @Bean
    public PricePersistenceMapper pricePersistenceMapper() {
        return new PricePersistenceMapper();
    }

    @Bean
    public PricePersistenceAdapter pricePersistenceAdapter(PriceRepository priceRepository, PricePersistenceMapper pricePersistenceMapper) {
        return new PricePersistenceAdapter(priceRepository, pricePersistenceMapper);
    }

    @Bean
    public PriceService priceService(PricePersistenceAdapter pricePersistenceAdapter) {
        return new PriceService(pricePersistenceAdapter);
    }

}
