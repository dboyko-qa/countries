package com.dboiko.countries.controller.graphql;

import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
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
    public Slice<CountryGraphql> countries(@Argument int page,@Argument int size) {
        return countryService.allCountriesGraphql(PageRequest.of (page, size));
    }

    //example of request for query with pagination
    //query {
    //  countries(page: 1, size: 10) {
    //    edges {
    //      node {
    //        id
    //        name
    //        code
    //      }
    //    }
    //    pageInfo{
    //      hasPreviousPage
    //      hasNextPage
    //    }
    //
    //  }
    //}

}
