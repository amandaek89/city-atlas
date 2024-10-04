package com.cityatlas.services;
import com.cityatlas.dtos.CountryDto;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

public class CountryService {

    private final CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    //hämta länder
    public List<CountryDto> getAllCountries() {
        return countryRepo.findAll()
                .stream()
                .map(country -> {
                    //skapa nytt CountryDto
                    CountryDto dto = new CountryDto();
                    dto.setId(country.getId());
                    dto.setName(country.getName());
                    dto.setLanguage(country.getLanguage());
                    dto.setPopulation(country.getPopulation());
                    return dto; //retur dto
                })
                .collect(Collectors.toList());
    }

    // Lägg till ett nytt land
    public CountryDto addCountry(CountryDto countryDto) {
        // Skapa en ny Country
        Country country = new Country();
        country.setName(countryDto.getName());
        country.setLanguage(countryDto.getLanguage());
        country.setPopulation(countryDto.getPopulation());

        //spara landet i databasen
        Country savedCountry = countryRepo.save(country);

        //skapa och retur en CountryDto
        CountryDto savedCountryDto = new CountryDto();
        savedCountryDto.setId(savedCountry.getId());
        savedCountryDto.setName(savedCountry.getName());
        savedCountryDto.setLanguage(savedCountry.getLanguage());
        savedCountryDto.setPopulation(savedCountry.getPopulation());

        return savedCountryDto; //retur det sparade Dto
    }

    //uppdatera land
    public CountryDto updateCountry(Long id, CountryDto updatedCountryDto) {
        return countryRepo.findById(id)
                .map(country -> {
                    country.setName(updatedCountryDto.getName());
                    country.setLanguage(updatedCountryDto.getLanguage());
                    country.setPopulation(updatedCountryDto.getPopulation());

                    Country savedCountry = countryRepo.save(country);

                    CountryDto savedCountryDto = new CountryDto();
                    savedCountryDto.setId(savedCountry.getId());
                    savedCountryDto.setName(savedCountry.getName());
                    savedCountryDto.setLanguage(savedCountry.getLanguage());
                    savedCountryDto.setPopulation(savedCountry.getPopulation());

                    return savedCountryDto;
                })
                .orElseThrow(() -> new RuntimeException("Country not found"));
    }

    //ta bort land
    public void deleteCountry(Long id) {
        countryRepo.deleteById(id);
    }
}

