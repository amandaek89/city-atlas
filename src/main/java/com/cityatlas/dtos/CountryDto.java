package com.cityatlas.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Dataöverföringsobjekt (DTO) för länder.
 * Används för att överföra landinformation mellan olika lager i applikationen.
 */
@Getter
@Setter
public class CountryDto {
    private Long id;
    private String name;
    private String language;
    private Long population;
    private Long continentID;

    /**
     * Standardkonstruktor för CountryDto.
     */
    public CountryDto() {}

    /**
     * Konstruktor för att initiera CountryDto med specifika värden.
     *
     * @param id          Landets ID (kommenterad för tillfället)
     * @param name        Landets namn
     * @param language    Språket som talas i landet
     * @param population  Antal invånare i landet
     * @param continentID Kopplingen till världsdel, om den finns
     */
    public CountryDto(Long id, String name, String language, Long population, Long continentID) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.population = population;
        this.continentID = continentID;
    }
}
