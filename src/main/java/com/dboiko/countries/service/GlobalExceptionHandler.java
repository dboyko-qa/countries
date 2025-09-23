package com.dboiko.countries.service;

import com.dboiko.countries.controller.error.ApiError;
import com.dboiko.countries.ex.CountryAlreadyExistsException;
import com.dboiko.countries.ex.CountryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${api.version}")
    private String apiVersion;
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleCountryNotFoundException(CountryNotFoundException ex, HttpServletRequest request) {
        LOG.error("Error while searching for country: {}. Request URI: {}", ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(new ApiError(
                apiVersion,
                HttpStatus.NOT_FOUND.toString(),
                "Country not found",
                request.getRequestURI(),
                ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CountryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleCountryAlreadyExistsException(CountryAlreadyExistsException ex, HttpServletRequest request) {
        LOG.error("Country exists: {}. Request URI: {}", ex.getMessage(), request.getRequestURI(), ex);
        return new ResponseEntity<>(new ApiError(
                apiVersion,
                HttpStatus.BAD_REQUEST.toString(),
                "Country already exists",
                request.getRequestURI(),
                ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

}
