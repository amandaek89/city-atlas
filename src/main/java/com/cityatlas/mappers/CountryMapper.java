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
                country.getContinent().getId() //hämtar id för Continent iom continent är parent
        );
    }

    //konvertera CountryDto till Country entity
    public Country toEntity(CountryDto countryDto, Continent continent) {
        return new Country(
                countryDto.getId(),
                countryDto.getName(),
                countryDto.getLanguage(),
                countryDto.getPopulation(),
                continent, //Continent som parent
                null
        );
    }
}
