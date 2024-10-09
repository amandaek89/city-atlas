package com.cityatlas.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryDto {
    private Long id; // Landets ID
    private String name; // Landets namn
    private String language; // Språket som talas
    private Long population; // Antal invånare
    private Long continentID; // Kopplingen till världsdel, om den finns

    public CountryDto() {} // Standardkonstruktor

    // Konstruktor för att initiera CountryDto med värden
    public CountryDto(Long id, String name, String language, Long population, Long continentID) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.population = population;
        this.continentID = continentID;
    }
}
