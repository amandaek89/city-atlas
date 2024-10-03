package com.cityatlas.controllers;

import com.cityatlas.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cityatlas.dtos.CountryDto;

import java.util.List;


@RestController
@RequestMapping("user/countries") //basurl för endpointsen om country
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService =countryService;
    }

    //hämta listan över länder
    @GetMapping
    public ResponseEntity<List<CountryDto>> getAllCountries() {
        List<CountryDto> countries = countryService.getAllCountries();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    //lägga till ett land
    @PostMapping
    public ResponseEntity<CountryDto> addCountry(@RequestBody CountryDto countryDto) {
        // Spara landet via servicen
        CountryDto savedCountry = countryService.addCountry(countryDto);
        return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
    }

    //uppdatera
    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        CountryDto updatedCountry = countryService.updatedCountry(id, countryDto);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    //ta bort land
    @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService. deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
