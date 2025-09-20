package com.dboiko.countries.ex;

public class CountryAlreadyExistsException extends RuntimeException {
    public CountryAlreadyExistsException() {
    }

    public CountryAlreadyExistsException(String message) {
        super(message);
    }
}
