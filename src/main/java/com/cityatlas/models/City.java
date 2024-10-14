package com.cityatlas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity // Markerar denna klass som en JPA-entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Stadens ID

    private String name; // Stadens namn
    private int population; // Antal invånare
    private String knownFor; // Vad staden är känd för

    @ManyToOne // Definierar en många-till-en relation med Country
    @JoinColumn(name = "country_id") // Anger namnet på kolumnen i databasen
    private Country country; // Kopplingen till landet

    // Standardkonstruktor
    public City() {}

    // Konstruktor för att initiera City med värden
    public City(String name, int population, String knownFor, Country country) {
        this.name = name;
        this.population = population;
        this.knownFor = knownFor;
        this.country = country;
    }
}
