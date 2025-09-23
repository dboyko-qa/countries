package com.dboiko.countries.ex;

public class CountryNotFoundException extends RuntimeException {
    public CountryNotFoundException() {
    }

    public CountryNotFoundException(String code) {
        super("Country code %s not found.".formatted(code));
    }
}
