package com.dboiko.countries.service;

import com.dboiko.countries.data.CountryEntity;
import com.dboiko.countries.data.CountryRepository;
import com.dboiko.countries.domain.Country;
import com.dboiko.countries.domain.graphql.CountryGraphql;
import com.dboiko.countries.domain.graphql.CountryInputGraphql;
import com.dboiko.countries.ex.CountryAlreadyExistsException;
import com.dboiko.countries.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public List<CountryGraphql> allCountriesGraphql() {
        List<CountryGraphql> result  = countryRepository.findAll()
                .stream()
                .map(el ->
                        new CountryGraphql(
                                el.getId(),
                                el.getName(),
                                el.getCode()
                        ))
                .toList();
        System.out.println(result);
        return result;
    }

    @Override
    public Country add(Country country) {
        if (!countryRepository.findByCodeIgnoreCase(country.code()).isPresent()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCode(country.code());
            countryEntity.setName(country.name());

            countryRepository.save(countryEntity);

            return new Country(countryEntity.getName(), countryEntity.getCode());

        }
        else {
            throw new CountryAlreadyExistsException("Country with code %s already exists".formatted(country.code()));
        }
    }

    @Override
    public CountryGraphql addGraphql(CountryInputGraphql country) {
        if (!countryRepository.findByCodeIgnoreCase(country.code()).isPresent()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCode(country.code());
            countryEntity.setName(country.name());

            countryRepository.save(countryEntity);

            return new CountryGraphql(countryEntity.getId(),
                    countryEntity.getName(), countryEntity.getCode());

        }
        else {
            throw new CountryAlreadyExistsException("Country with code %s already exists".formatted(country.code()));
        }
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
    public CountryGraphql updateByCodeGraphql(String code, String name) {
        CountryEntity countryEntity = countryRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new CountryNotFoundException("Country code not found."));

        countryEntity.setName(name);
        CountryEntity updated = countryRepository.save(countryEntity);
        return new CountryGraphql(updated.getId(), updated.getName(), updated.getCode());
    }

    @Override
    public void deleteByCode(String code) {
        CountryEntity countryEntity = countryRepository.findByCodeIgnoreCase(code)
                        .orElseThrow(() -> new CountryNotFoundException("Country code not found."));
        countryRepository.delete(countryEntity);
    }

    @Override
    public Boolean deleteByCodeGraphql(String code) {
        CountryEntity countryEntity = countryRepository.findByCodeIgnoreCase(code)
                        .orElseThrow(() -> new CountryNotFoundException("Country code not found."));
        if (countryEntity != null) {
            try {
                countryRepository.delete(countryEntity);
                return true;
            }
            catch (DataAccessException ex) {
                return false;
            }
        }
        return false;
    }

}
