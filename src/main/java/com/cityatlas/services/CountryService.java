package com.cityatlas.services;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.mappers.CountryMapper;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import org.springframework.stereotype.Service;
import com.cityatlas.models.Continent;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepo countryRepo;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepo countryRepo, CountryMapper countryMapper) {
        this.countryRepo = countryRepo;
        this.countryMapper = countryMapper;
    }

    //hämta alla länder
    public List<CountryDto> getAllCountries() {
        return countryRepo.findAll()
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }


    //lägg till ett nytt land
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = countryMapper.toEntity(countryDto);
        Country savedCountry = countryRepo.save(country);
        return countryMapper.toDto(savedCountry);
    }

    //uppdatera ett land
    public CountryDto updateCountry(Long id, CountryDto updatedCountryDto) {
        return countryRepo.findById(id)
                .map(country -> {
                    country.setName(updatedCountryDto.getName());
                    country.setLanguage(updatedCountryDto.getLanguage());
                    country.setPopulation(updatedCountryDto.getPopulation());
                    Country savedCountry = countryRepo.save(country);
                    return countryMapper.toDto(savedCountry);
                })
                .orElseThrow(() -> new RuntimeException("Kunde inte hitta landet."));
    }

    //ta bort ett land
    public void deleteCountry(Long id) {
        countryRepo.deleteById(id);
    }
}
