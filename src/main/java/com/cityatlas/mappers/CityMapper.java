package com.cityatlas.mappers;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.models.City;
import com.cityatlas.models.Country;

public class CityMapper {

    // Konverterar en City-entity till en CityDto
    public static CityDto toDto(City city) {
        return new CityDto(
                city.getId(),
                city.getName(),
                city.getPopulation(),
                city.getKnownFor(),
                city.getCountry().getId()  // Returnerar endast landets ID
        );
    }

    // Konverterar en CityDto till en City-entity
    public static City toEntity(CityDto cityDto, Country country) {
        return new City(
                cityDto.getName(),
                cityDto.getPopulation(),
                cityDto.getKnownFor(),
                country  // Använd country-objektet som hämtats
        );
    }
}
