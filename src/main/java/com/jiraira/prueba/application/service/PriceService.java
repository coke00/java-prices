package com.jiraira.prueba.application.service;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.domain.model.constant.Constants;
import com.jiraira.prueba.domain.ports.input.GetPriceUseCase;
import com.jiraira.prueba.domain.ports.output.PriceOutputPort;

import java.time.LocalDateTime;
import java.util.Comparator;


public class PriceService implements GetPriceUseCase {

    private final PriceOutputPort priceOutputPort;

    public PriceService(PriceOutputPort priceOutputPort) {
        this.priceOutputPort = priceOutputPort;
    }

    @Override
    public Price findApplicablePrice(Integer productId, Integer brandId, LocalDateTime applicationDate) throws PriceNotFoundException {

        return priceOutputPort.findPriceApplicableByProductIdBrandIdAndDate(productId, brandId, applicationDate)
                .stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException(String.format(Constants.PRICE_NOT_FOUND_FORMAT, productId, brandId, applicationDate)));
    }

}
