package com.jiraira.prueba.infrastructure.adapters.input.rest;

import com.jiraira.prueba.domain.model.Price;
import com.jiraira.prueba.domain.ports.input.GetPriceUseCase;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.ErrorResponse;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.PriceResponse;
import com.jiraira.prueba.infrastructure.adapters.input.rest.mapper.PriceRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceRestAdapter {

    private final GetPriceUseCase getPriceUseCase;
    private PriceRestMapper priceRestMapper;

    public PriceRestAdapter(GetPriceUseCase getPriceUseCase, PriceRestMapper priceRestMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceRestMapper = priceRestMapper;
    }

    @GetMapping
    @Operation(summary = "Get price", description = "Get price by some filters", tags = {"Price"}, parameters = {
            @Parameter(name = "applicationDate", description = "The date and time of the application", required = true, example = "2020-06-14T19:00:59"),
            @Parameter(name = "productId", description = "The ID of the product", required = true, example = "35455"),
            @Parameter(name = "brandId", description = "The ID of the brand", required = true, example = "1"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Correcta obtención de price", content = @Content(schema = @Schema(implementation = PriceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parameter not accepted", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Parameter is missing", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Price not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<PriceResponse> getPrice(@RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
                                                  @RequestParam("productId") Integer productId,
                                                  @RequestParam("brandId") Integer brandId) {
        Price price = getPriceUseCase.findApplicablePrice(productId, brandId, applicationDate);
        return ResponseEntity.ok(priceRestMapper.toPriceResponse(price));
    }
}
