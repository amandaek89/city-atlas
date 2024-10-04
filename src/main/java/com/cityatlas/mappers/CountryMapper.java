package com.cityatlas.mappers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.models.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {

    //Country till CountryDto
    public CountryDto toDto(Country country) {
        CountryDto dto = new CountryDto();
        dto.setId(country.getId());
        dto.setName(country.getName());
        dto.setLanguage(country.getLanguage());
        dto.setPopulation(country.getPopulation());
        //continent id från parent
        dto.setContinentID(country.getContinent().getId());
        return dto;
    }

    //CountryDto till Country
    public Country toEntity(CountryDto dto) {
        Country country = new Country();
        country.setName(dto.getName());
        country.setLanguage(dto.getLanguage());
        country.setPopulation(dto.getPopulation());
        //koppla continent även här?
        return country;
    }
}
