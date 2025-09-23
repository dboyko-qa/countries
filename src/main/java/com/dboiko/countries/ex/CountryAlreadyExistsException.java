package com.dboiko.countries.ex;

public class CountryAlreadyExistsException extends RuntimeException {
    public CountryAlreadyExistsException() {
    }

    public CountryAlreadyExistsException(String code) {
        super("Country with code %s already exists".formatted(code));
    }
}
