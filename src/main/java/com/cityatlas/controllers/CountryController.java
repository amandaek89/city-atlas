package com.cityatlas.controllers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.services.CountryService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


import java.util.List;

@RestController
//endpoint alla länder
@RequestMapping("/user/countries")
@Tag(name = "Countries", description = "Endpoints for managing countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    //hämta alla länder
    @GetMapping
    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }


    //lägg till ett nytt land
    @PostMapping
    @Operation(summary = "Add a new country", description = "Adds a new country to the database")
    public CountryDto addCountry(@RequestBody CountryDto countryDto) {
        return countryService.addCountry(countryDto);
    }

    //uppdatera ett land
    @PutMapping("/admin/{id}")
    @Operation(summary = "Update a country", description = "Updates the details of an existing country")
    public CountryDto updateCountry(@PathVariable Long id, @RequestBody CountryDto updatedCountryDto) {
        return countryService.updateCountry(id, updatedCountryDto);
    }

    //ta bort ett land
    @DeleteMapping("/admin/{id}")
    @Operation(summary = "Delete a country", description = "Deletes a country from the database")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }
}