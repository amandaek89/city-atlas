package com.cityatlas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDto {

    private Long id; // Stadens ID
    private String name; // Stadens namn
    private int population; // Antal invånare
    private String knownFor; // Vad staden är känd för
    private Long countryId;  // Kopplingen till landet via dess ID

    public CityDto() {} // Standardkonstruktor

    // Konstruktor för att initiera CityDto med värden
    public CityDto(Long id, String name, int population, String knownFor, Long countryId) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.knownFor = knownFor;
        this.countryId = countryId;
    }
}
