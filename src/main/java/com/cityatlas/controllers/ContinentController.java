package com.cityatlas.controllers;

import com.cityatlas.dtos.ContinentDto;
import com.cityatlas.dtos.CountryDto;
import com.cityatlas.services.ContinentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContinentController {

    @Autowired
    private ContinentService continentService;

    @GetMapping("/user/continents")
    @Operation(summary = "Get all continents", description = "Retrieves a list of all continents")
    public ResponseEntity<List<ContinentDto>> getAllContinents() {
        return ResponseEntity.ok(continentService.getAllContinents());
    }




    @PostMapping("/user/continents")
    @Operation(summary = "Add a new continent", description = "Adds a new continent to the database")
    public ResponseEntity<ContinentDto> addContinent(@RequestBody ContinentDto continentDto) {
        return ResponseEntity.ok(continentService.addContinent(continentDto));
    }

    @PutMapping("/admin/continents/{id}")
    @Operation(summary = "Update a continent", description = "Updates the details of an existing continent")
    public ResponseEntity<ContinentDto> updateContinent(@PathVariable Long id, @RequestBody ContinentDto updatedContinentDto) {
        return ResponseEntity.ok(continentService.updateContinent(id, updatedContinentDto));
    }

    @DeleteMapping ("/admin/continents/{id}")
    @Operation(summary = "Delete a continent", description = "Deletes a continent from the database")
    public ResponseEntity<Void> deleteContinent(@PathVariable Long id) {
        continentService.deleteContinent(id);
        return ResponseEntity.ok().build();
    }

}