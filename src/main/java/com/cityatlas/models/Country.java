package com.cityatlas.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table (name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //automatiskt generering av ett id
    private Long id;
    private String name;
    private String language;
    private Long population;

    //child till världsdel
    @ManyToOne
    @JoinColumn (name = "continent_id", nullable = false) //forgein key till världsdel
    private Continent continent;

    //parent till city
    //orphanremove så citys till country tas bort vid radering
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<City> cities;


<<<<<<< HEAD
}
=======
}
>>>>>>> cec51dd1e07f8defe7efe8a94fc90fbcb845ea9e
