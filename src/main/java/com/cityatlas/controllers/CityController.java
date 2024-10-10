package com.cityatlas.controllers;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.services.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Kontrollklass för hantering av städer.
 * Tillhandahåller endpoints för att hämta, lägga till, uppdatera och ta bort städer.
 */
@RestController
@RequestMapping("/user/cities")
@Tag(name = "Cities", description = "Endpoints for managing cities")
public class CityController {

    private final CityService cityService;

    /**
     * Konstruktor för att initialisera CityController med CityService.
     *
     * @param cityService tjänst för hantering av städer
     */
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Hämtar en lista över alla städer.
     *
     * @return en lista med {@link CityDto} som representerar alla städer
     */
    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieves a list of all cities")
    public ResponseEntity<List<CityDto>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    /**
     * Hämtar en stad baserat på ID.
     *
     * @param id ID för staden som ska hämtas
     * @return {@link CityDto} som representerar den begärda staden
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get city by ID", description = "Retrieves a city by its ID")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }

    /**
     * Lägger till en ny stad.
     *
     * @param cityDto dataöverföringsobjekt som innehåller information om den nya staden
     * @return det tillagda {@link CityDto} objektet
     */
    @PostMapping
    @Operation(summary = "Add a new city", description = "Adds a new city to the database")
    public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) {
        return ResponseEntity.ok(cityService.addCity(cityDto));
    }

    /**
     * Uppdaterar information om en stad.
     *
     * @param id              ID för den stad som ska uppdateras
     * @param updatedCityDto dataöverföringsobjekt som innehåller uppdaterad information för staden
     * @return det uppdaterade {@link CityDto} objektet
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a city", description = "Updates the details of an existing city")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @RequestBody CityDto updatedCityDto) {
        return ResponseEntity.ok(cityService.updateCity(id, updatedCityDto));
    }

    /**
     * Tar bort en stad från databasen.
     *
     * @param id ID för den stad som ska tas bort
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a city", description = "Deletes a city from the database")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }
}
