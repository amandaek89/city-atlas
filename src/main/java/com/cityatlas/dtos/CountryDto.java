/*package com.cityatlas.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Dataöverföringsobjekt (DTO) för länder.
 * Används för att överföra landinformation mellan olika lager i applikationen.
 */
/*@Getter
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
    /*public CountryDto() {}

    /**
     * Konstruktor för att initiera CountryDto med specifika värden.
     *
     * @param id          Landets ID (kommenterad för tillfället)
     * @param name        Landets namn
     * @param language    Språket som talas i landet
     * @param population  Antal invånare i landet
     * @param continentID Kopplingen till världsdel, om den finns
     */
    /*public CountryDto(Long id, String name, String language, Long population, Long continentID) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.population = population;
        this.continentID = continentID;
    }
}*/
package com.cityatlas.dtos;

import com.cityatlas.models.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Long id;
    private String name;
    private String language;
    private Long population;
    private Long continentID;

    // Konstruktor för att skapa CountryDto från Country
    public CountryDto(Country country) {
        this.id = country.getId();
        this.name = country.getName();
        this.language = country.getLanguage();
        this.population = country.getPopulation();
        // Om du har continent kan du hämta den här
        this.continentID = country.getContinent() != null ? country.getContinent().getId() : null;
    }

    // Om du har en metod för att konvertera tillbaka till en Country
    public Country toEntity() {
        Country country = new Country();
        country.setId(this.id);
        country.setName(this.name);
        country.setLanguage(this.language);
        country.setPopulation(this.population);
        // Här kan du också sätta continentID om det är relevant
        return country;
    }
}

