package com.cityatlas.controllers;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.services.CityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities") // Endpoint för städer
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Hämta alla städer (tillgänglig för både admin och användare)
    @GetMapping
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    // Hämta en stad baserat på ID (tillgänglig för både admin och användare)
    @GetMapping("/{id}")
    public CityDto getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // Lägg till en ny stad (endast för admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public CityDto addCity(@RequestBody CityDto cityDto) {
        return cityService.addCity(cityDto);
    }

    // Uppdatera information om en stad (endast för admin)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CityDto updateCity(@PathVariable Long id, @RequestBody CityDto updatedCityDto) {
        return cityService.updateCity(id, updatedCityDto);
    }

    // Ta bort en stad (endast för admin)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
