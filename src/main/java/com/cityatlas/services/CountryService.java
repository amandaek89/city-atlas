package com.cityatlas.services;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.mappers.CountryMapper;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import org.springframework.stereotype.Service;
import com.cityatlas.models.Continent;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service-klass för hantering av länder.
 * Innehåller metoder för att hämta, lägga till, uppdatera och ta bort länder.
 */
@Service
public class CountryService {

    private final CountryRepo countryRepo;
    private final CountryMapper countryMapper;

    /**
     * Konstruktor för att initiera CountryService med CountryRepo och CountryMapper.
     *
     * @param countryRepo  repository för att interagera med landdata
     * @param countryMapper mappare för att konvertera mellan Country och CountryDto
     */
    public CountryService(CountryRepo countryRepo, CountryMapper countryMapper) {
        this.countryRepo = countryRepo;
        this.countryMapper = countryMapper;
    }

    /**
     * Hämtar en lista över alla länder.
     *
     * @return en lista av CountryDto som representerar alla länder
     */
    public List<CountryDto> getAllCountries() {
        return countryRepo.findAll()
                .stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Lägger till ett nytt land.
     *
     * @param countryDto det CountryDto som representerar det nya landet
     * @return det sparade CountryDto
     */
    public CountryDto addCountry(CountryDto countryDto) {
        Country country = countryMapper.toEntity(countryDto);
        Country savedCountry = countryRepo.save(country);
        return countryMapper.toDto(savedCountry);
    }

    /**
     * Uppdaterar ett befintligt land.
     *
     * @param id                ID för landet som ska uppdateras
     * @param updatedCountryDto det CountryDto med uppdaterad information
     * @return det uppdaterade CountryDto
     * @throws RuntimeException om landet inte hittas
     */
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

    /**
     * Tar bort ett land baserat på dess ID.
     *
     * @param id ID för landet som ska tas bort
     */
    public void deleteCountry(Long id) {
        countryRepo.deleteById(id);
    }
}
