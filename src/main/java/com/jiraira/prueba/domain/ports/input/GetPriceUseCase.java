package com.jiraira.prueba.domain.ports.input;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.domain.model.Price;

import java.time.LocalDateTime;


public interface GetPriceUseCase {

    Price findApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) throws PriceNotFoundException;
}
