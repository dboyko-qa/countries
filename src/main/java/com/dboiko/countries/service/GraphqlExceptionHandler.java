package com.dboiko.countries.service;

import com.dboiko.countries.ex.CountryAlreadyExistsException;
import com.dboiko.countries.ex.CountryNotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class GraphqlExceptionHandler {

    @GraphQlExceptionHandler(CountryAlreadyExistsException.class)
    public GraphQLError handleCountryAlreadyExistsException(CountryAlreadyExistsException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.BAD_REQUEST)
                .extensions(Map.of("errorCode", "COUNTRY_ALREADY_EXISTS"))
                .build();
    }

    @GraphQlExceptionHandler(CountryNotFoundException.class)
    public GraphQLError handleCountryNotFoundException(CountryNotFoundException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .errorType(ErrorType.NOT_FOUND)
                .extensions(Map.of("errorCode", "COUNTRY_NOT_FOUND"))
                .build();
    }

}
