package com.dboiko.countries.service;

import com.dboiko.countries.domain.Country;
import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.domain.graphql.CountryInputGraphql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CountryService {
    List<Country> allCountries();

    Slice<CountryGraphql> allCountriesGraphql(Pageable pageable);

    Country add(Country country);

    CountryGraphql addGraphql(CountryInputGraphql country);

    Country updateByCode(String code, String name);

    CountryGraphql updateByCodeGraphql(String code, String name);

    void deleteByCode(String code);

    Boolean deleteByCodeGraphql(String code);
}
