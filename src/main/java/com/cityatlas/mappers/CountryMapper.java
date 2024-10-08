package com.cityatlas.mappers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.models.Continent;
import com.cityatlas.models.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    //konvertera Country entity till CountryDto
    public CountryDto toDto(Country country) {
        return new CountryDto(
                country.getId(),
                country.getName(),
                country.getLanguage(),
                country.getPopulation(),
                country.getContinent() != null ? country.getContinent().getId() : null // Hämtar id för Continent om den finns
        );
    }

    //konvertera CountryDto till Country entity utan continent
    public Country toEntity(CountryDto countryDto) {
        return new Country(
                countryDto.getId(),
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                null //ingen continent
        );
    }

    //konvertera CountryDto till Country entity med continent
    public Country toEntity(CountryDto countryDto, Continent continent) {
        return new Country(
                countryDto.getId(),
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                continent //continent som parent
        );
    }
}
