package com.dboiko.countries.controller.graphql;

import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.domain.graphql.CountryInputGraphql;
import com.dboiko.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {
    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGraphql add(@Argument CountryInputGraphql country) {
        return countryService.addGraphql(country);
    }


    @MutationMapping
    public CountryGraphql updateByCode(@Argument String code,
                                       @Argument String name) {
        return countryService.updateByCodeGraphql(code, name);
    }

    @MutationMapping
    public Boolean deleteByCode(@Argument String code) {
        return countryService.deleteByCodeGraphql(code);
    }

}
