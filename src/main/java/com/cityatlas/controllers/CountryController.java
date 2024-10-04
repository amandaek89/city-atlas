package com.cityatlas.controllers;

import com.cityatlas.dtos.CountryDto;
import com.cityatlas.services.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//endpoint alla länder
@RequestMapping("/api/countries")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    //hämta alla länder
    @GetMapping
    public List<CountryDto> getAllCountries() {
        return countryService.getAllCountries();
    }


    //lägg till ett nytt land
    @PostMapping
    public CountryDto addCountry(@RequestBody CountryDto countryDto) {
        return countryService.addCountry(countryDto);
    }

    //uppdatera ett land
    @PutMapping("/{id}")
    public CountryDto updateCountry(@PathVariable Long id, @RequestBody CountryDto updatedCountryDto) {
        return countryService.updateCountry(id, updatedCountryDto);
    }

    //ta bort ett land
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }
}