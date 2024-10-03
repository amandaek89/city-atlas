package com.cityatlas.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Long id;
    private String name;
    private String language;
    private Long population;
    private Long continentID; //ref till världsdel då det är childpost
}
