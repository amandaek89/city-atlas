/*package com.cityatlas.dtos;

import com.cityatlas.models.Continent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContinentDto {
    private Long id;
    private String name;
    private List<CountryDto> countries;

    public ContinentDto(Continent continent) {
    }

    public Continent toEntity() {
        return new Continent(id, name);
    }
}*/
package com.cityatlas.dtos;

import com.cityatlas.models.Continent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContinentDto {
    private Long id;
    private String name;
    private List<CountryDto> countries;

    // Konstruktor för att skapa en ContinentDto från en Continent
    public ContinentDto(Continent continent) {
        this.id = continent.getId();
        this.name = continent.getName();
        // Om du vill inkludera länderna
        this.countries = continent.getCountries() != null ? continent.getCountries().stream()
                .map(CountryDto::new)
                .collect(Collectors.toList()) : null;
    }

    // Metod för att konvertera tillbaka till en Continent
    public Continent toEntity() {
        Continent continent = new Continent();
        continent.setId(this.id);
        continent.setName(this.name);
        // Om du vill inkludera länderna
        return continent;
    }
}
