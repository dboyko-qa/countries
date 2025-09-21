package com.dboiko.countries.controller.graphql;

import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CountryQueryController {
    private final CountryService countryService;

    @Autowired
    public CountryQueryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public List<CountryGraphql> countries() {
        return countryService.allCountriesGraphql();
    }

}
