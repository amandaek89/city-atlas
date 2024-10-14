package com.cityatlas.controllers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.services.CountryService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Kontrollklass för hantering av länder.
 * Tillhandahåller endpoints för att hämta, lägga till, uppdatera och ta bort länder.
 */
@RestController
@Tag(name = "Countries", description = "Endpoints for managing countries")
public class CountryController {

    private final CountryService countryService;

    /**
     * Konstruktor för att initialisera CountryController med CountryService.
     *
     * @param countryService tjänst för hantering av länder
     */
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Hämtar en lista över alla länder.
     *
     * @return en lista med {@link CountryDto} som representerar alla länder
     */
    @GetMapping("/user/countries")
    @Operation(summary = "Get all countries", description = "Retrieves a list of all countries")
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }

    /**
     * Lägger till ett nytt land.
     *
     * @param countryDto dataöverföringsobjekt som innehåller information om det nya landet
     * @return det tillagda {@link CountryDto} objektet
     */
    @PostMapping("user/countries")
    @Operation(summary = "Add a new country", description = "Adds a new country to the database")
    public CountryDto addCountry(@RequestBody CountryDto countryDto) {
        return countryService.addCountry(countryDto);
    }

    /**
     * Uppdaterar ett lands information.
     *
     * @param id              ID för det land som ska uppdateras
     * @param updatedCountryDto dataöverföringsobjekt som innehåller uppdaterad information för landet
     * @return det uppdaterade {@link CountryDto} objektet
     */
    @PutMapping("/admin/countries/{id}")
    @Operation(summary = "Update a country", description = "Updates the details of an existing country")
    public CountryDto updateCountry(@PathVariable Long id, @RequestBody CountryDto updatedCountryDto) {
        return countryService.updateCountry(id, updatedCountryDto);
    }

    /**
     * Hämtar ett specifikt land baserat på dess ID.
     *
     * @param id ID för det land som ska hämtas
     * @return det begärda {@link CountryDto} objektet
     */
    @GetMapping("/user/countries/{id}")
    @Operation(summary = "Get a country by ID", description = "Retrieves a country by its ID")
    public CountryDto getCountryById(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }


    /**
     * Tar bort ett land från databasen.
     *
     * @param id ID för det land som ska tas bort
     */
    @DeleteMapping("/admin/countries/{id}")
    @Operation(summary = "Delete a country", description = "Deletes a country from the database")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }
}
