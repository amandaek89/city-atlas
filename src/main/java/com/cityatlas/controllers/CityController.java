package com.cityatlas.controllers;

import com.cityatlas.dtos.CityDto;
import com.cityatlas.services.CityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities") // Endpoint för städer
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    // Hämta alla städer
    @GetMapping
    public List<CityDto> getAllCities() {
        return cityService.getAllCities();
    }

    // Hämta en stad baserat på ID
    @GetMapping("/{id}")
    public CityDto getCityById(@PathVariable Long id) {
        return cityService.getCityById(id);
    }

    // Lägg till en ny stad
    @PostMapping
    public CityDto addCity(@RequestBody CityDto cityDto) {
        return cityService.addCity(cityDto);
    }

    // Uppdatera information om en stad
    @PutMapping("/{id}")
    public CityDto updateCity(@PathVariable Long id, @RequestBody CityDto updatedCityDto) {
        return cityService.updateCity(id, updatedCityDto);
    }

    // Ta bort en stad
    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
