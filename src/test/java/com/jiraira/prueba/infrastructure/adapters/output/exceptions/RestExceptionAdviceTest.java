package com.jiraira.prueba.infrastructure.adapters.output.exceptions;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RestExceptionAdviceTest {
    @InjectMocks
    RestExceptionAdvice restExceptionAdvice;

    @Test
    void handlePriceNotFound() {
        PriceNotFoundException priceNotFoundException = new PriceNotFoundException("this price not exist.");
        ResponseEntity<Object> handlePriceNotFound = restExceptionAdvice.handlePriceNotFound(priceNotFoundException, null);
        assertEquals(404, handlePriceNotFound.getStatusCodeValue());
        assertEquals("Price not found: this price not exist.", ((ErrorResponse) handlePriceNotFound.getBody()).getMessage());

    }

    @Test
    void handleTypesException() {
        MethodArgumentTypeMismatchException argumentTypeMismatchException = new MethodArgumentTypeMismatchException("type is not accepted", null, null, null, null);
        ResponseEntity<Object> response = restExceptionAdvice.handleTypesException(argumentTypeMismatchException, null);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Parameter not accepted: Failed to convert value of type 'java.lang.String'", ((ErrorResponse) response.getBody()).getMessage());

    }

    @Test
    void handleEndpointNotFound() {
        NoResourceFoundException noResourceFoundException = new NoResourceFoundException(HttpMethod.GET, "Endpoint not found");
        ResponseEntity<Object> response = restExceptionAdvice.handleEndpointNotFound(noResourceFoundException, null);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Page not found: No static resource Endpoint not found.", ((ErrorResponse) response.getBody()).getMessage());
    }

    @Test
    void handleParametersException() {
        MissingServletRequestParameterException missingServletRequestParameterException = new MissingServletRequestParameterException("productId", "Integer");
        ResponseEntity<Object> response = restExceptionAdvice.handleParametersException(missingServletRequestParameterException, null);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Error found: Required request parameter 'productId' for method parameter type Integer is not present", ((ErrorResponse) response.getBody()).getMessage());
    }
}
