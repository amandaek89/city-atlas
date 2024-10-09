package com.cityatlas.controllers;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.services.CityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/cities") // Endpoint för städer
@Tag(name = "City API", description = "API för att hantera städer") // Swagger-Tag för att beskriva controllern
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Hämta alla städer (tillgänglig för både admin och användare)
    @GetMapping
    @Operation(summary = "Hämta alla städer", description = "Returnerar en lista över alla städer.") // Swagger-operation för metoden
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    // Hämta en stad baserat på ID (tillgänglig för både admin och användare)
    @GetMapping("/{id}")
    @Operation(summary = "Hämta en stad", description = "Returnerar en stad baserat på dess ID.") // Swagger-operation för metoden
    public CityDto getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // Lägg till en ny stad (endast för admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lägg till en ny stad", description = "Lägger till en ny stad, endast tillgänglig för administratörer.") // Swagger-operation för metoden
    public CityDto addCity(@RequestBody CityDto cityDto) {
        return cityService.addCity(cityDto);
    }

    // Uppdatera information om en stad (endast för admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Uppdatera en stad", description = "Uppdaterar informationen om en stad, endast för administratörer.") // Swagger-operation för metoden
    public CityDto updateCity(@PathVariable Long id, @RequestBody CityDto updatedCityDto) {
        return cityService.updateCity(id, updatedCityDto);
    }

    // Ta bort en stad (endast för admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ta bort en stad", description = "Tar bort en stad baserat på dess ID, endast för administratörer.") // Swagger-operation för metoden
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
