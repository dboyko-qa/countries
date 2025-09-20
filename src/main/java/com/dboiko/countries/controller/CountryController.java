package com.dboiko.countries.controller;

import com.dboiko.countries.domain.Country;
import com.dboiko.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> all() {
        return countryService.allCountries();
    }

    //example of request: curl -X POST http://localhost:8282/api/country/add \
    //  -H "Content-Type: application/json" \
    //  -d '{"name":"Germany","code":"DE"}'
    @PostMapping("/add")
    public Country add(@RequestBody Country country) {
        return countryService.add(country);
    }

    //example of request: curl -X PATCH "http://localhost:8282/api/country/update/RU?name=Russian%20Federation"
    @PatchMapping("/update/{code}")
    public Country updateByCode(@PathVariable String code,
                                @RequestParam String name) {
        return countryService.updateByCode(code, name);
    }

    @DeleteMapping("/delete/{code}")
    public void deleteByCode(@PathVariable String code) {
        countryService.deleteByCode(code);
    }


}
