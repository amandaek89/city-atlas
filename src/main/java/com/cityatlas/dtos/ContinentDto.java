package com.cityatlas.dtos;

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
}