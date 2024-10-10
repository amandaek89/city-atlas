package com.cityatlas.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * Entity-klass som representerar ett land.
 * Innehåller information om landets namn, språk, befolkning och koppling till en världsdel.
 */
@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatiskt generering av ett id
    private Long id;

    private String name; // Landets namn
    private String language; // Språket som talas i landet
    private Long population; // Antal invånare i landet

    // Child till världsdel - KAN VARA NULL
    @ManyToOne
    @JoinColumn(name = "continent_id", nullable = true) // Foreign key till världsdel
    private Continent continent;

    // Parent till city
    // Orphan removal så cities till country tas bort vid radering
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;

    /**
     * Konstruktor för att initiera Country med namn, språk, befolkning och världsdel.
     *
     * @param name      landets namn
     * @param language  språket som talas i landet
     * @param population antalet invånare i landet
     * @param continent världsdel som landet tillhör
     */
    public Country(String name, String language, Long population, Continent continent) {
        this.name = name;
        this.language = language;
        this.population = population;
        this.continent = continent;
    }

    /**
     * Konstruktor för att initiera Country med ID, namn, språk, befolkning och världsdel.
     *
     * @param id        landets ID
     * @param name      landets namn
     * @param language  språket som talas i landet
     * @param population antalet invånare i landet
     * @param continent världsdel som landet tillhör
     */
    public Country(Long id, String name, String language, Long population, Continent continent) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.population = population;
        this.continent = continent;
    }
}
