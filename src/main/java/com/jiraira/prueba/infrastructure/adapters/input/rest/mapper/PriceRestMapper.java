package com.jiraira.prueba.infrastructure.adapters.input.rest.mapper;
import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.PriceResponse;



public class PriceRestMapper {
    public PriceResponse toPriceResponse(Price price){
        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .curr(price.getCurr())
                .build();
    }
}
