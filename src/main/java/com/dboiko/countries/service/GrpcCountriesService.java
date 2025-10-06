package com.dboiko.countries.service;

import com.dboiko.countries.*;
import com.dboiko.countries.domain.Country;
import com.dboiko.countries.ex.CountryNotFoundException;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrpcCountriesService extends CountriesServiceGrpc.CountriesServiceImplBase {

    private final CountryService countryService;

    public GrpcCountriesService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void allCountries(Empty request, StreamObserver<CountryResponse> responseObserver)  {
        List<Country> allCountries = countryService.allCountries();
        for (Country country : allCountries) {
            responseObserver.onNext(
                    CountryResponse.newBuilder()
                            .setName(country.name())
                            .setCode(country.code())
                            .build()
            );
        }
        responseObserver.onCompleted();

    }


    @Override
    public void addCountry(CountryRequest countryRequest, StreamObserver<CountryResponse> responseObserver){
        Country country = countryService.add(new Country(countryRequest.getName(), countryRequest.getCode()));

        responseObserver.onNext(
                CountryResponse.newBuilder()
                        .setCode(country.code())
                        .setName(country.name())
                        .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public void deleteCountry(CountryCodeRequest countryCodeRequest, StreamObserver<Empty> responseObserver) {
        try {
            countryService.deleteByCode(countryCodeRequest.getCode());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (CountryNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }

    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<AddCountriesSummary> responseObserver) {

        return new StreamObserver<CountryRequest>() {
            int addedCountries = 0;
            @Override
            public void onNext(CountryRequest value) {
                countryService.add(new Country(value.getName(), value.getCode()));
                ++addedCountries;
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(AddCountriesSummary.newBuilder()
                                .setTotalAdded(addedCountries)
                                .build());
                responseObserver.onCompleted();
            }
        };
    }
}
