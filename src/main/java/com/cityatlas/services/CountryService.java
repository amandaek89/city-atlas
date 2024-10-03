package com.cityatlas.services;
import com.cityatlas.dtos.CountryDto;
import com.cityatlas.models.Country;
import com.cityatlas.repositories.CountryRepo;
import org.springframework.stereotype.Service;
import com.cityatlas.repositories.ContinentRepo;

import java.util.List;
import java.util.stream.Collectors;

public class CountryService {

    private final CountryRepo countryRepo;
    private final ContinentRepo continentRepo;

    public CountryService(CountryRepo countryRepo, ContinentRepo continentRepo) {
        this.countryRepo = countryRepo;
        this.continentRepo = continentRepo;
    }

    //hämta länder
    // Hämta alla länder
    public List<CountryDto> getAllCountries() {
        return countryRepo.findAll() // Hämta alla länder från databasen
                .stream() // Gör om listan till en stream
                .map(country -> {
                    // Skapa ett nytt CountryDto
                    CountryDto dto = new CountryDto();
                    dto.setId(country.getId()); // Sätt ID
                    dto.setName(country.getName()); // Sätt namn
                    dto.setLanguage(country.getLanguage()); // Sätt språk
                    dto.setPopulation(country.getPopulation()); // Sätt befolkning
                    // Kontinenten tas bort här
                    return dto; // Returera DTO
                })
                .collect(Collectors.toList()); // Samla resultaten i en lista
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

    //ta bort land
    public void deleteCountry(Long id) {
        countryRepo.deleteById(id);
    }
}
