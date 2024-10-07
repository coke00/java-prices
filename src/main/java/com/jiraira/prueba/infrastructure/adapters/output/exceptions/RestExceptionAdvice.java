package com.jiraira.prueba.infrastructure.adapters.output.exceptions;

import com.jiraira.prueba.domain.exception.PriceNotFoundException;
import com.jiraira.prueba.domain.model.constant.Constants;
import com.jiraira.prueba.infrastructure.adapters.input.rest.data.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class RestExceptionAdvice {
    @ExceptionHandler(PriceNotFoundException.class)
    protected ResponseEntity<Object> handlePriceNotFound(PriceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), String.format(Constants.MESSAGE_ERROR_PRICE_NOT_FOUND, ex.getMessage()), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleTypesException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), String.format(Constants.MESSAGE_ERROR_TYPE_ERROR, ex.getMessage()), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<Object> handleEndpointNotFound(NoResourceFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), String.format(Constants.MESSAGE_ERROR_PAGE_NOT_FOUND, ex.getMessage()), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<Object> handleParametersException(MissingServletRequestParameterException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), String.format(Constants.MESSAGE_ERROR, ex.getMessage()), null), HttpStatus.BAD_REQUEST);
    }

}
