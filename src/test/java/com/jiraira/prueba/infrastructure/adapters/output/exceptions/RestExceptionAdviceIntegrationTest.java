package com.jiraira.prueba.infrastructure.adapters.output.exceptions;

import com.jiraira.prueba.PruebaApplication;
import com.jiraira.prueba.domain.model.constant.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = PruebaApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("integration")
class RestExceptionAdviceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.US);
    }

    @Test
    void handlePriceNotFound() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.of(2023, 6, 14, 19, 0, 59);
        Integer productId = 35454;
        Integer brandId = 1;

        mockMvc.perform(get("/prices")
                        .param("applicationDate", applicationDate.toString())
                        .param("productId", String.valueOf(productId))
                        .param("brandId", String.valueOf(brandId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(String.format(Constants.MESSAGE_ERROR_PRICE_NOT_FOUND, "Applicable price not found for productId: 35454, brandId: 1, and date: 2023-06-14T19:00:59.")));
    }

    @Test
    void handleMissingParametersException() throws Exception {

        mockMvc.perform(get("/prices")
                        .param("otherParams", "MissingServletRequestParameterException"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(String.format(Constants.MESSAGE_ERROR, "Required request parameter 'applicationDate' for method parameter type LocalDateTime is not present")));
    }

    @Test
    void handleTypesException() throws Exception {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 19, 0, 59);
        Integer productId = 35454;

        mockMvc.perform(get("/prices")
                        .param("applicationDate", applicationDate.toString())
                        .param("productId", String.valueOf(productId))
                        .param("brandId", "s"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(String.format(Constants.MESSAGE_ERROR_TYPE_ERROR, "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Integer'; For input string: \"s\"")));
    }

    @Test
    void handleEndpointNotFound() throws Exception {
        mockMvc.perform(get("/no-exist-endpoint")
                        .param("trigger", "PageNotFoundException"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(String.format(Constants.MESSAGE_ERROR_PAGE_NOT_FOUND, "No static resource no-exist-endpoint.")));
    }
}
