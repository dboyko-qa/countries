package com.dboiko.countries.service;

import com.dboiko.countries.domain.Country;

import java.util.List;

public interface CountryService {
    List<Country> allCountries();
    Country add(Country country);
    Country updateByCode(String code, String name);
}
