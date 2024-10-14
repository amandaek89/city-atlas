/*package com.cityatlas.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity // Markerar denna klass som en JPA-entity
@Getter
@Setter
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Världsdelens ID

    private String name;

    // Definierar en en-till-många relation med Country
    //orphanremove så citys till country tas bort vid radering
    @OneToMany(mappedBy = "continent",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Country> countries;

    public Continent(long l, String europe) {
    }
}*/
package com.cityatlas.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor; // Importera NoArgsConstructor
import lombok.Setter;

import java.util.List;

@Entity // Markerar denna klass som en JPA-entity
@Getter
@Setter
@NoArgsConstructor // Lägg till en standardkonstruktor
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Världsdelens ID

    private String name;

    // Definierar en en-till-många relation med Country
    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Country> countries;

    // Konstruktor för att skapa en Continent med id och namn
    public Continent(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}

