package com.dboiko.countries.service;

import com.dboiko.countries.data.CountryEntity;
import com.dboiko.countries.data.CountryRepository;
import com.dboiko.countries.domain.Country;
import com.dboiko.countries.ex.CountryAlreadyExistsException;
import com.dboiko.countries.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        List<Country> result  = countryRepository.findAll()
                .stream()
                .map(el ->
                        new Country(
                                el.getName(),
                                el.getCode()
                        ))
                .toList();
        System.out.println(result);
        return result;
    }

    @Override
    public Country add(Country country) {
        if (countryRepository.findByCodeIgnoreCase(country.code()).isPresent()) {
            throw new CountryAlreadyExistsException("Country with code %s already exists".formatted(country.code()));
        }

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode(country.code());
        countryEntity.setName(country.name());

        countryRepository.save(countryEntity);

        return new Country(countryEntity.getName(), countryEntity.getCode());

    }

    @Override
    public Country updateByCode(String code, String name) {
        CountryEntity countryEntity = countryRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new CountryNotFoundException("Country code not found."));

        countryEntity.setName(name);
        CountryEntity updated = countryRepository.save(countryEntity);
        return new Country(updated.getName(), updated.getCode());
    }

    @Override
    public void deleteByCode(String code) {
        CountryEntity countryEntity = countryRepository.findByCodeIgnoreCase(code)
                        .orElseThrow(() -> new CountryNotFoundException("Country code not found."));
        countryRepository.delete(countryEntity);
    }

}
