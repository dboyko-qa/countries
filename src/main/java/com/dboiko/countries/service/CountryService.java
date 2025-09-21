package com.dboiko.countries.service;

import com.dboiko.countries.domain.Country;
import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.domain.graphql.CountryInputGraphql;

import java.util.List;

public interface CountryService {
    List<Country> allCountries();

    List<CountryGraphql> allCountriesGraphql();

    Country add(Country country);

    CountryGraphql addGraphql(CountryInputGraphql country);

    Country updateByCode(String code, String name);

    CountryGraphql updateByCodeGraphql(String code, String name);

    void deleteByCode(String code);

    Boolean deleteByCodeGraphql(String code);
}
